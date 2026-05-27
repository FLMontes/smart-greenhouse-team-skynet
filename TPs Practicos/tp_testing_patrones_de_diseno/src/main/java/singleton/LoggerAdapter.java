package singleton;

import singleton.Logger;

public class LoggerAdapter implements ILogger {

    @Override
    public void logWarning(String msg) {
        Logger.getInstance().logWarning(msg);
    }

    @Override
    public void logDebug(String msg) {
        Logger.getInstance().logDebug(msg);
    }

    @Override
    public void logInfo(String msg) {
        Logger.getInstance().logInfo(msg);
    }

    @Override
    public void logError(String msg) {
        Logger.getInstance().logError(msg);
    }
}
