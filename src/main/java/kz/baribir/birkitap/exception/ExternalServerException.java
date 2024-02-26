package kz.baribir.birkitap.exception;

public class ExternalServerException extends CustomException{
    public ExternalServerException(String ex){
        loggableBehaviour = new Loggable(ex);
    }
    @Override
    public int getResultCode() {
        return -6;
    }

    @Override
    public String getResultMessage() {
        return "Сыртқы сервер қателігі!";
    }
}
