package pl.tourpol.backend.security.exception;

public class RequestException extends RuntimeException{

    private final RequestErrorMessage requestErrorMessage;
    public RequestException(RequestErrorMessage requestErrorMessage) {
        this.requestErrorMessage = requestErrorMessage;
    }

    public RequestErrorMessage getRequestErrorMessage() {
        return requestErrorMessage;
    }
}
