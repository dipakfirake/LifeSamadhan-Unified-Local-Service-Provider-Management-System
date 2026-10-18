import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login as loginAPI } from '../api/auth';
import { useAuth } from '../context/AuthContext';
import Alert from '../components/common/Alert';
import '../styles/auth.css';

const Login = () => {
  const navigate = useNavigate();
  const { login, isAuthenticated, role } = useAuth();
  
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  
  const [errors, setErrors] = useState({});
  const [alert, setAlert] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const validate = () => {
    const newErrors = {};
    
    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email is invalid';
    }
    
    if (!formData.password) {
      newErrors.password = 'Password is required';
    }
    
    return newErrors;
  };

  useEffect(() => {
    if (isAuthenticated) {
      if (role === 'ADMIN') {
        navigate('/admin/dashboard');
      } else if (role === 'CUSTOMER') {
        navigate('/customer/dashboard');
      } else if (role === 'SERVICEPROVIDER') {
        navigate('/provider/dashboard');
      } else {
        navigate('/');
      }
    }
  }, [isAuthenticated, role, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const newErrors = validate();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    setLoading(true);
    setAlert(null);

    try {
      const response = await loginAPI(formData);
      
      
      
      login(response.token, response, response.role);
      
      
    } catch (error) {
      const msg = error.response?.data?.message || (typeof error.response?.data === 'string' ? error.response.data : error.message) || 'Login failed.';
      setAlert({
        type: 'error',
        message: msg,
      });
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <div className="auth-header">
          <h1>Welcome Back</h1>
          <p>Login to your LifeSamadhan account</p>
        </div>

        {alert && (
          <Alert
            type={alert.type}
            message={alert.message}
            onClose={() => setAlert(null)}
          />
        )}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <label className="form-label" htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              autoComplete="email"
              className={`form-input ${errors.email ? 'error' : ''}`}
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter your email"
            />
            {errors.email && <span className="form-error">{errors.email}</span>}
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              autoComplete="current-password"
              className={`form-input ${errors.password ? 'error' : ''}`}
              value={formData.password}
              onChange={handleChange}
              placeholder="Enter password"
            />
            {errors.password && <span className="form-error">{errors.password}</span>}
          </div>

          <button 
            type="submit" 
            className="btn btn-primary w-full btn-lg"
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <div className="demo-accounts-section" style={{ marginTop: '1.5rem', paddingTop: '1rem', borderTop: '1px dashed #e2e8f0' }}>
          <p style={{ fontSize: '0.85rem', fontWeight: 600, color: '#64748b', marginBottom: '0.6rem', textAlign: 'center' }}>
            ⚡ Quick Demo Logins (Click to Autofill):
          </p>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(140px, 1fr))', gap: '0.5rem' }}>
            <button
              type="button"
              className="btn btn-outline"
              style={{ fontSize: '0.78rem', padding: '0.45rem', border: '1px solid #cbd5e1', borderRadius: '6px', background: '#f8fafc', cursor: 'pointer' }}
              onClick={() => {
                setFormData({ email: 'admin@mail.com', password: 'Admin@LifeSamadan123' });
                setErrors({});
              }}
            >
              👑 Admin
            </button>
            <button
              type="button"
              className="btn btn-outline"
              style={{ fontSize: '0.78rem', padding: '0.45rem', border: '1px solid #cbd5e1', borderRadius: '6px', background: '#f8fafc', cursor: 'pointer' }}
              onClick={() => {
                setFormData({ email: 'sachin.deshmukh@mail.com', password: 'Provider@123' });
                setErrors({});
              }}
            >
              🔧 Plumber (Mumbai)
            </button>
            <button
              type="button"
              className="btn btn-outline"
              style={{ fontSize: '0.78rem', padding: '0.45rem', border: '1px solid #cbd5e1', borderRadius: '6px', background: '#f8fafc', cursor: 'pointer' }}
              onClick={() => {
                setFormData({ email: 'ganesh.pawar@mail.com', password: 'Provider@123' });
                setErrors({});
              }}
            >
              ⚡ Electrician (Navi Mumbai)
            </button>
            <button
              type="button"
              className="btn btn-outline"
              style={{ fontSize: '0.78rem', padding: '0.45rem', border: '1px solid #cbd5e1', borderRadius: '6px', background: '#f8fafc', cursor: 'pointer' }}
              onClick={() => {
                setFormData({ email: 'aniket.kulkarni@mail.com', password: 'Customer@123' });
                setErrors({});
              }}
            >
              🏠 Customer (Mumbai)
            </button>
            <button
              type="button"
              className="btn btn-outline"
              style={{ fontSize: '0.78rem', padding: '0.45rem', border: '1px solid #cbd5e1', borderRadius: '6px', background: '#f8fafc', cursor: 'pointer' }}
              onClick={() => {
                setFormData({ email: 'pooja.salunkhe@mail.com', password: 'Customer@123' });
                setErrors({});
              }}
            >
              🏠 Customer (Navi Mumbai)
            </button>
          </div>
        </div>

        <div className="auth-footer">
          <p>Don't have an account? <Link to="/register">Register here</Link></p>
        </div>
      </div>
    </div>
  );
};

export default Login;
