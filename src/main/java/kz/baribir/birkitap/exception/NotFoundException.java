package kz.baribir.birkitap.exception;

public class NotFoundException extends CustomException{
    public NotFoundException(){
        loggableBehaviour = new NoLoggable();
    }

    @Override
    public int getResultCode() {
        return -2;
    }

    @Override
    public String getResultMessage() {
        return "Деректер табылмады";
    }
}
