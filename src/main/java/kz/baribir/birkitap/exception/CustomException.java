package kz.baribir.birkitap.exception;


import kz.baribir.birkitap.util.PojUtil;

public abstract class CustomException extends RuntimeException{
    protected LoggableBehaviour loggableBehaviour;
    public abstract int getResultCode();
    public abstract String getResultMessage();
    public String loggingMessage(){
        return loggableBehaviour.loggingMessage();
    }
    public static CustomException convert2CustomException(Exception exception){
        if(exception instanceof CustomException){
            return (CustomException)exception;
        }
        return new ServiceException(PojUtil.getStackTrace(exception));
    }

}
