package kz.baribir.birkitap.exception;


import kz.baribir.birkitap.util.PojUtil;

public class DocumentGeneratingException extends CustomException{
    public DocumentGeneratingException(Exception e){
        loggableBehaviour = new Loggable(PojUtil.getStackTrace(e));
    }
    public DocumentGeneratingException(String e){
        loggableBehaviour = new Loggable(e);
    }

    @Override
    public String getResultMessage() {
        return "Құжат жасау қателігі";
    }

    @Override
    public int getResultCode() {
        return -6;
    }
}
