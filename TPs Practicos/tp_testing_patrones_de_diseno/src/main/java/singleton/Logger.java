package singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//Clase Logger para el singleton
public class Logger {
    private static Logger logger;
    private Logger(){

    }
    //Con esta seccion lo convertimos en un singleton
    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }return logger;
    }

    public void logWarning(String msg){
        log(msg,"\u001B[33m"," [WARN]");
    }

    public void logDebug(String msg){
        log(msg,"\u001B[32m"," [DEBUG]");
    }
    public void logInfo(String msg){
        log(msg,"\u001B[37m"," [INFO]");
    }
    public void logError(String msg){
        log(msg,"\u001B[31m"," [ERROR] ");
    }

    private void log(String msg, String color, String prefijo){
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String reset = "\u001B[0m";
        System.out.println(color + " " + dateTime + " " + prefijo + " " + msg + reset );
    }
}