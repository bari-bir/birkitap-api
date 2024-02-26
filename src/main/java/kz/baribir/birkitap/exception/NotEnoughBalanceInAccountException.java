package kz.baribir.birkitap.exception;

public class NotEnoughBalanceInAccountException extends CustomException{
    public NotEnoughBalanceInAccountException(){
        loggableBehaviour = new NoLoggable();
    }

    @Override
    public int getResultCode() {
        return 10;
    }

    @Override
    public String getResultMessage() {
        return "Баланс жеткіліксіз!";
    }
}
