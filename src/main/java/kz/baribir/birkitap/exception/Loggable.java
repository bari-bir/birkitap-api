package kz.baribir.birkitap.exception;

public class Loggable implements LoggableBehaviour{
    private String logMessage;
    public Loggable(String logMessage){
        this.logMessage = logMessage;
    }
    @Override
    public String loggingMessage() {
        return logMessage;
    }
}
