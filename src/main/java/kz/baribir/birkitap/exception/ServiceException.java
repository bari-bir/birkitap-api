package kz.baribir.birkitap.exception;

public class ServiceException extends CustomException{
    public ServiceException(String e){
        loggableBehaviour = new Loggable(e);
    }

    @Override
    public String getResultMessage() {
        return "Сервер қателігі";
    }

    @Override
    public int getResultCode() {
        return -5;
    }
}
