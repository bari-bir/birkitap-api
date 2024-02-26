package kz.baribir.birkitap.exception;

public class NoLoggable implements LoggableBehaviour{
    @Override
    public String loggingMessage() {
        return null;
    }
}
