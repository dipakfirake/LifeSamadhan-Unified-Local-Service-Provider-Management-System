import React, { useState, useEffect } from 'react';
import { getMyAssignments, acceptAssignment, rejectAssignment, startService, completeService, getEarnings } from '../../api/provider';
import Alert from '../../components/common/Alert';
import { useAuth } from '../../context/AuthContext';
import notificationService from '../../services/notificationService';
import './ProviderDashboard.css';

const CountdownTimer = ({ startTime, durationMinutes = 30 }) => {
    const [timeLeft, setTimeLeft] = useState('');

    useEffect(() => {
        const calculateTime = () => {
            if (!startTime) return false;

            
            let normalizedStart = startTime;
            if (typeof startTime === 'string' && !startTime.endsWith('Z') && !startTime.includes('+')) {
                normalizedStart = startTime + 'Z';
            }

            const start = new Date(normalizedStart).getTime();
            const now = new Date().getTime();
            let diff = (start + durationMinutes * 60 * 1000) - now;
            
            
            
            if (diff > 18000000) { 
                 diff = diff - 19800000; 
            }

            if (diff <= 0) {
                setTimeLeft('Expired');
                return false;
            } else {
                const mins = Math.floor(diff / (1000 * 60));
                const secs = Math.floor((diff % (1000 * 60)) / 1000);
                setTimeLeft(`${mins}m ${secs}s left`);
                return true;
            }
        };

        calculateTime();
        const timer = setInterval(() => {
            if (!calculateTime()) clearInterval(timer);
        }, 1000);

        return () => clearInterval(timer);
    }, [startTime, durationMinutes]);

    return (
        <span style={{ 
            color: timeLeft === 'Expired' ? '#ef4444' : '#f59e0b', 
            fontWeight: '600',
            fontSize: '0.85rem',
            background: timeLeft === 'Expired' ? '#fee2e2' : '#fff7ed',
            padding: '2px 8px',
            borderRadius: '12px',
            marginLeft: '8px',
            border: '1px solid currentColor'
        }}>
            {timeLeft === 'Expired' ? '⌛ Expired' : `🕒 ${timeLeft}`}
        </span>
    );
};

const ProviderDashboard = () => {
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState('active'); 
  const [assignments, setAssignments] = useState([]);
  const [earningsData, setEarningsData] = useState({ totalEarnings: 0, totalJobs: 0, totalHours: 0, history: [] });
  const [otpInputs, setOtpInputs] = useState({}); 
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState(null);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  useEffect(() => {
    loadData();
  }, [refreshTrigger]);

  useEffect(() => {
    const handleNotification = (msg) => {
      console.log("Real-time Update:", msg);
      setAlert({ type: 'info', message: typeof msg === 'string' ? msg : 'New update received!' });
      setRefreshTrigger(p => p + 1);
    };

    notificationService.onReceiveNotification(handleNotification);
  }, []);

  const loadData = async () => {
    try {
      const data = await getMyAssignments();
      setAssignments(data);
      const earn = await getEarnings();
      setEarningsData(earn);
    } catch (err) {
      console.error(err);
    }
  };

  
  const getErrorMessage = (err) => {
      const data = err.response?.data;
      if (data?.message) return data.message; 
      if (typeof data === 'string') return data; 
      return 'Action failed'; 
  };

  const handleAccept = async (id) => {
    try {
      await acceptAssignment(id);
      setAlert({ type: 'success', message: 'Assignment Accepted!' });
      setRefreshTrigger(p => p + 1);
    } catch (err) {
      setAlert({ type: 'error', message: getErrorMessage(err) });
    }
  };

  const handleReject = async (id) => {
    if (!window.confirm("Reject this assignment?")) return;
    try {
      await rejectAssignment(id);
      setAlert({ type: 'info', message: 'Assignment Rejected' });
      setRefreshTrigger(p => p + 1);
    } catch (err) {
      setAlert({ type: 'error', message: getErrorMessage(err) });
    }
  };

  const handleStart = async (id) => {
    const otp = otpInputs[id];
    if (!otp) {
      setAlert({ type: 'error', message: 'Please enter OTP from customer' });
      return;
    }
    try {
      
      
      
      await startService(id, `"${otp}"`); 
      setAlert({ type: 'success', message: 'Service Started!' });
      setRefreshTrigger(p => p + 1);
    } catch (err) {
      setAlert({ type: 'error', message: getErrorMessage(err) });
    }
  };

  const handleComplete = async (id) => {
    if (!window.confirm("Mark service as completed?")) return;
    try {
      await completeService(id);
      setAlert({ type: 'success', message: 'Service Completed!' });
      setRefreshTrigger(p => p + 1);
    } catch (err) {
      setAlert({ type: 'error', message: getErrorMessage(err) });
    }
  };

  const handleOtpChange = (id, value) => {
    setOtpInputs(prev => ({ ...prev, [id]: value }));
  };
  const checkExpired = (startTime) => {
    if (!startTime) return false;
    let normalizedStart = startTime;
    if (typeof startTime === 'string' && !startTime.endsWith('Z') && !startTime.includes('+')) {
      normalizedStart = startTime + 'Z';
    }
    const start = new Date(normalizedStart).getTime();
    const now = new Date().getTime();
    let diff = (start + 30 * 60 * 1000) - now;
    if (diff > 18000000) diff -= 19800000;
    return diff <= 0;
  };

  // Identifies jobs whose scheduled time or response window has already passed
  const isAssignmentBeforeTime = (item) => {
    if (!item?.assignment) return false;
    // Status completed, rejected, cancelled, timeout are history
    if (['COMPLETED', 'REJECTED', 'CANCELLED_BY_CUSTOMER', 'TIMEOUT', 'CANCELLED'].includes(item.assignment.status)) {
      return true;
    }
    // Started jobs are currently actively in progress
    if (item.assignment.status === 'STARTED') {
      return false;
    }
    // If scheduled date/time has passed (with 15 min buffer)
    if (item.request?.scheduledDate) {
      const scheduledTime = new Date(item.request.scheduledDate).getTime();
      if (!isNaN(scheduledTime) && scheduledTime < Date.now() - 15 * 60 * 1000) {
        return true;
      }
    }
    // If response timer has expired
    const isExpired = checkExpired(item.assignment.status === 'ASSIGNED' ? item.assignment.assignedAt : item.assignment.acceptedAt);
    if (isExpired) {
      return true;
    }
    return false;
  };

  const activeAssignments = assignments.filter(a => 
    ['ASSIGNED', 'ACCEPTED', 'STARTED'].includes(a.assignment.status) && !isAssignmentBeforeTime(a));
    
  const historyAssignments = assignments.filter(a => 
    ['COMPLETED', 'REJECTED', 'CANCELLED_BY_CUSTOMER', 'TIMEOUT', 'CANCELLED'].includes(a.assignment.status) || isAssignmentBeforeTime(a));

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1>Provider Dashboard</h1>
        <p>Welcome, {user?.name}</p>
        <div className="tabs">
          <button 
            className={`tab-btn ${activeTab === 'active' ? 'active' : ''}`}
            onClick={() => setActiveTab('active')}
          >
            Active Jobs ({activeAssignments.length})
          </button>
          <button 
            className={`tab-btn ${activeTab === 'history' ? 'active' : ''}`}
            onClick={() => setActiveTab('history')}
          >
            History ({historyAssignments.length})
          </button>
          <button 
            className={`tab-btn ${activeTab === 'earnings' ? 'active' : ''}`}
            onClick={() => setActiveTab('earnings')}
          >
            My Earnings
          </button>
        </div>
      </div>

      <div className="container mt-4">
        {alert && <Alert type={alert.type} message={alert.message} onClose={() => setAlert(null)} />}

        {activeTab === 'active' && (
          <div className="assignments-list">
             {activeAssignments.length === 0 ? (
                <div className="card text-center p-6" style={{ background: '#f8fafc', border: '1px dashed #cbd5e1', borderRadius: '8px', padding: '30px' }}>
                  <p style={{ margin: 0, color: '#475569', fontSize: '1rem', fontWeight: 600 }}>No active upcoming jobs right now.</p>
                  <p style={{ margin: '6px 0 16px', color: '#94a3b8', fontSize: '0.875rem' }}>Expired requests or past scheduled jobs are moved to the History tab.</p>
                  {historyAssignments.length > 0 && (
                    <button className="btn btn-outline btn-sm" onClick={() => setActiveTab('history')}>
                      View History ({historyAssignments.length})
                    </button>
                  )}
                </div>
             ) : (
               activeAssignments.map(item => {
                 const isExpired = checkExpired(item.assignment.status === 'ASSIGNED' ? item.assignment.assignedAt : item.assignment.acceptedAt);
                 const scheduledDateObj = item.request?.scheduledDate ? new Date(item.request.scheduledDate) : null;
                 const formattedScheduled = scheduledDateObj && !isNaN(scheduledDateObj.getTime())
                   ? scheduledDateObj.toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })
                   : 'Flexible / As scheduled';

                 return (
                  <div key={item.assignment.id} className="request-card card">
                     <div className="request-header">
                       <span className={`status-badge ${item.assignment.status.toLowerCase()}`}>
                         {item.assignment.status}
                       </span>
                       {item.assignment.status === 'ASSIGNED' && (
                         <CountdownTimer startTime={item.assignment.assignedAt} />
                       )}
                       {item.assignment.status === 'ACCEPTED' && (
                         <CountdownTimer startTime={item.assignment.acceptedAt} />
                       )}
                       <span className="date">
                         Booked: {new Date(item.assignment.assignedAt).toLocaleDateString()}
                       </span>
                     </div>
                     <div className="request-body">
                       <h4>{item.customer?.name} ({item.customer?.mobile || 'N/A'})</h4>
                       <p><strong>Service:</strong> {item.service?.name || item.category?.name || 'General Service'}</p>
                       <p><strong>Address:</strong> {item.request?.serviceAddress}</p>
                       <p><strong>Scheduled:</strong> {formattedScheduled}</p>
                       
                       {item.assignment.status === 'ACCEPTED' && !isExpired && (
                         <div className="otp-input-group mt-2">
                            <input 
                               type="text" 
                               placeholder="Enter OTP" 
                               className="form-input otp-input"
                               maxLength="4"
                               value={otpInputs[item.assignment.id] || ''}
                               onChange={(e) => handleOtpChange(item.assignment.id, e.target.value)}
                            />
                         </div>
                       )}
                     </div>
                     <div className="request-actions column-actions">
                       {item.assignment.status === 'ASSIGNED' && (
                         <>
                           <button className="btn btn-success" onClick={() => handleAccept(item.assignment.id)}>Accept</button>
                           <button className="btn btn-danger" onClick={() => handleReject(item.assignment.id)}>Reject</button>
                         </>
                       )}
                       {item.assignment.status === 'ACCEPTED' && !isExpired && (
                         <button className="btn btn-primary" onClick={() => handleStart(item.assignment.id)}>Start Service</button>
                       )}
                       {item.assignment.status === 'STARTED' && (
                         <button className="btn btn-primary" onClick={() => handleComplete(item.assignment.id)}>Complete Job</button>
                       )}
                     </div>
                  </div>
                 );
               })
             )}
          </div>
        )}

        {activeTab === 'history' && (
          <div className="assignments-list">
             {historyAssignments.length === 0 ? (
                <p className="text-center">No history found.</p>
             ) : (
                historyAssignments.map(item => {
                  const isElapsed = isAssignmentBeforeTime(item) && !['COMPLETED', 'CANCELLED', 'REJECTED'].includes(item.assignment.status);
                  const scheduledDateObj = item.request?.scheduledDate ? new Date(item.request.scheduledDate) : null;
                  const formattedScheduled = scheduledDateObj && !isNaN(scheduledDateObj.getTime())
                    ? scheduledDateObj.toLocaleString([], { dateStyle: 'medium', timeStyle: 'short' })
                    : 'Flexible';

                  return (
                    <div key={item.assignment.id} className="request-card card faded" style={isElapsed ? { borderLeft: '4px solid #ef4444' } : {}}>
                       <div className="request-header">
                          <span className={`status-badge ${isElapsed ? 'cancelled' : item.assignment.status.toLowerCase()}`}>
                            {isElapsed ? 'EXPIRED' : item.assignment.status}
                          </span>
                          <span className="date">
                            Booked: {new Date(item.assignment.assignedAt).toLocaleDateString()}
                          </span>
                       </div>
                       <div className="request-body" style={{display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start'}}>
                          <div>
                             <h4>{item.customer?.name} ({item.customer?.mobile || 'N/A'})</h4>
                             <p><strong>Service:</strong> {item.service?.name || item.category?.name || 'General Service'}</p>
                             <p><strong>Scheduled:</strong> {formattedScheduled}</p>
                             {isElapsed && (
                               <p style={{ color: '#ef4444', fontSize: '0.8rem', fontWeight: 600, margin: '4px 0' }}>
                                 ⌛ Time elapsed or assignment response expired
                               </p>
                             )}
                             
                             {item.assignment.status === 'COMPLETED' && (
                               <div className="mt-2">
                                 {item.rating ? (
                                   <div>
                                     <div style={{color: '#f59e0b', fontWeight: 'bold', fontSize: '1.1rem'}}>
                                       {Array(item.rating.stars).fill('★').join('')}
                                     </div>
                                     {item.rating.comment && <p style={{fontSize: '0.9rem', color: '#555', fontStyle: 'italic'}}>"{item.rating.comment}"</p>}
                                   </div>
                                 ) : (
                                   <p className="text-sm text-gray-400">No review yet</p>
                                 )}
                               </div>
                             )}
                          </div>

                          {item.assignment.status === 'COMPLETED' && (
                            <div style={{textAlign: 'right', minWidth: '120px'}}>
                              <p style={{fontSize: '0.85rem', color: '#64748b', lineHeight: '1.4'}}>
                                Completed<br/>
                                <strong style={{color: '#333'}}>{new Date(item.assignment.completedAt).toLocaleDateString()}</strong><br/>
                                {new Date(item.assignment.completedAt).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                              </p>
                            </div>
                          )}
                       </div>
                    </div>
                  );
                })
             )}
          </div>
        )}
        {activeTab === 'earnings' && (
          <div className="earnings-section">
            <div className="stats-grid" style={{display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '20px', marginBottom: '30px'}}>
              <div className="stat-card card p-4 text-center">
                <span className="stat-label" style={{color: '#64748b', fontSize: '0.9rem'}}>Total Earnings</span>
                <h2 className="stat-value" style={{color: '#059669'}}>₹{earningsData.totalEarnings}</h2>
              </div>
              <div className="stat-card card p-4 text-center">
                <span className="stat-label" style={{color: '#64748b', fontSize: '0.9rem'}}>Avg. Rating</span>
                <h2 className="stat-value" style={{color: '#f59e0b'}}>
                  {earningsData.averageRating || 0} <span style={{fontSize: '1.2rem'}}>★</span>
                  <span style={{fontSize: '0.75rem', color: '#94a3b8', display: 'block', fontWeight: 'normal'}}>
                    ({earningsData.ratingCount || 0} reviews)
                  </span>
                </h2>
              </div>
              <div className="stat-card card p-4 text-center">
                <span className="stat-label" style={{color: '#64748b', fontSize: '0.9rem'}}>Hours Worked</span>
                <h2 className="stat-value" style={{color: '#2563eb'}}>{earningsData.totalHours} hr</h2>
              </div>
              <div className="stat-card card p-4 text-center">
                <span className="stat-label" style={{color: '#64748b', fontSize: '0.9rem'}}>Jobs Completed</span>
                <h2 className="stat-value" style={{color: '#7c3aed'}}>{earningsData.totalJobs}</h2>
              </div>
            </div>

            <h3>Payment History</h3>
            <div className="history-list mt-3">
              {earningsData.history.length === 0 ? (
                 <p className="text-center">No payment records found.</p>
              ) : (
                <div className="table-responsive">
                  <table className="table" style={{width: '100%', borderCollapse: 'collapse'}}>
                    <thead>
                      <tr style={{textAlign: 'left', borderBottom: '2px solid #e2e8f0'}}>
                        <th style={{padding: '12px'}}>Date</th>
                        <th style={{padding: '12px'}}>Service</th>
                        <th style={{padding: '12px'}}>Amount</th>
                        <th style={{padding: '12px'}}>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {earningsData.history.map(item => (
                        <tr key={item.id} style={{borderBottom: '1px solid #f1f5f9'}}>
                          <td style={{padding: '12px'}}>{new Date(item.completionDate).toLocaleDateString()}</td>
                          <td style={{padding: '12px'}}>{item.service}</td>
                          <td style={{padding: '12px', fontWeight: 'bold'}}>₹{item.amount}</td>
                          <td style={{padding: '12px'}}><span className="status-badge paid">PAID</span></td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProviderDashboard;
