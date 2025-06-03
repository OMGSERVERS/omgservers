package com.omgservers.connector.exception;

public class ClientSideHttpException extends RuntimeException {

    final int statusCode;
    final String responseText;

    public ClientSideHttpException(final int statusCode, final String responseText) {
        super(String.format("client http error, statusCode=%d, responseText=%s", statusCode, responseText));
        this.statusCode = statusCode;
        this.responseText = responseText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseText() {
        return responseText;
    }
}
