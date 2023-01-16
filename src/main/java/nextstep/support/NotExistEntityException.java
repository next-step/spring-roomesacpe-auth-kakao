package nextstep.support;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
        super();
    }

    public NotExistEntityException(String message) {
        super(message);
    }


    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistEntityException(Throwable cause) {
        super(cause);
    }
}
