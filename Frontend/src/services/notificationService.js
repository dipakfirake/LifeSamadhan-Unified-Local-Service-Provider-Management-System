import signalRService from './signalr';
import springBootService from './springboot-websocket';

class NotificationService {
    constructor() {
        this.service = null;
    }

    startConnection(token, user) {
        const localType = localStorage.getItem('VITE_BACKEND_TYPE');
        const envType = import.meta.env.VITE_BACKEND_TYPE;
        const type = (localType === 'SPRINGBOOT' || envType === 'SPRINGBOOT') ? 'SPRINGBOOT' : (localType || envType || 'SPRINGBOOT');
        this.service = (type === 'DOTNET') ? signalRService : springBootService;
        console.log(`Starting notifications for detected type: ${type}`);

        return this.service.startConnection(token, user);
    }

    onReceiveNotification(callback) {
        if (this.service) {
            this.service.onReceiveNotification(callback);
        } else {
            console.warn("NotificationService: Service not initialized yet.");
        }
    }

    stopConnection() {
        if (signalRService.stopConnection) signalRService.stopConnection();
        if (springBootService.stopConnection) springBootService.stopConnection();
    }
}

export default new NotificationService();
