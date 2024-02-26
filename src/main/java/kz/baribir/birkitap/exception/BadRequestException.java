package kz.baribir.birkitap.exception;

public class BadRequestException extends CustomException{
    private String resultMessage;
    public BadRequestException(String resultMessage){
        loggableBehaviour = new NoLoggable();
        this.resultMessage = resultMessage;
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public int getResultCode() {
        return -1;
    }
}
