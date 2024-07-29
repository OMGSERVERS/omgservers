package com.omgservers.service.operation.parseBasicAuthorizationHeader;

import com.omgservers.schema.dto.basicCredentials.BasicCredentialsDto;

public interface ParseBasicAuthorizationHeaderOperation {

    BasicCredentialsDto parseBasicAuthorizationHeader(String authorizationHeader);
}
