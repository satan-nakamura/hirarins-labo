package labo.hirarins.legacy.app.batch;

import labo.hirarins.legacy.app.ApplicationRuntimeException;

public class BatchRuntimeException extends ApplicationRuntimeException {

    private static final long serialVersionUID = 1L;

    public BatchRuntimeException() {
    }

    public BatchRuntimeException(String message) {
        super(message);
    }

    public BatchRuntimeException(Throwable cause) {
        super(cause);
    }

    public BatchRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BatchRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}