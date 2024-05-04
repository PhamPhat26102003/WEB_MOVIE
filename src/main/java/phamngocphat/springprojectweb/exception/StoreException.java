package phamngocphat.springprojectweb.exception;

public class StoreException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public StoreException(String message){
        super(message);
    }

    public StoreException(String message, Throwable exception){
        super(message, exception);
    }
}
