package singleton;

public class Logger {
    private static Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logWarning(String msg) {
        System.out.println("[WARN] " + msg);
    }
    
    public void logDebug(String msg) {
        System.out.println("[DEBUG] " + msg);
    }

    public void logInfo(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public void logError(String msg) {
        System.out.println("[ERROR] " + msg);
    }
}