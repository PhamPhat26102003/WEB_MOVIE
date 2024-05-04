package phamngocphat.springprojectweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FileNotFoudException extends RuntimeException{

    public FileNotFoudException(String message){
        super(message);
    }

    public FileNotFoudException(String message, Throwable exception){
        super(message, exception);
    }
}
