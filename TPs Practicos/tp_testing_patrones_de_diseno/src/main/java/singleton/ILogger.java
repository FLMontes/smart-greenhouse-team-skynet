package singleton;

public interface ILogger {
    void logWarning(String msg);
    void logDebug(String msg);
    void logInfo(String msg);
    void logError(String msg);

}
