package kz.baribir.birkitap.exception;

public class TokenExpiredException extends CustomException{
    public TokenExpiredException(){
        loggableBehaviour = new NoLoggable();
    }

    @Override
    public int getResultCode() {
        return -3;
    }

    @Override
    public String getResultMessage() {
        return "Токен уақыты өтіп кетті!";
    }
}
