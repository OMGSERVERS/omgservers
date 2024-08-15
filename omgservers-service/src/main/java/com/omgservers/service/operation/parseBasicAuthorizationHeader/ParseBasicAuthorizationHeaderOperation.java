package com.omgservers.service.operation.parseBasicAuthorizationHeader;

import com.omgservers.service.operation.parseBasicAuthorizationHeader.dto.BasicCredentialsDto;

public interface ParseBasicAuthorizationHeaderOperation {

    BasicCredentialsDto parseBasicAuthorizationHeader(String authorizationHeader);
}
