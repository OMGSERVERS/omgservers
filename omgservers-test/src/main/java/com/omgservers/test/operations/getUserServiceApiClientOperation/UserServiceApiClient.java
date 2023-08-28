package com.omgservers.test.operations.getUserServiceApiClientOperation;

import com.omgservers.test.security.ServiceAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(ServiceAccountCredentialsHeadersFactory.class)
public interface UserServiceApiClient extends UserServiceApi {
}
