package com.omgservers.service.operation.security;

public interface ParseBasicAuthorizationHeaderOperation {

    BasicCredentialsDto parseBasicAuthorizationHeader(String authorizationHeader);
}
