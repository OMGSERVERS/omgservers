package com.omgservers.test.operations.getTenantServiceApiClientOperation;

import com.omgservers.test.security.ServiceAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(ServiceAccountCredentialsHeadersFactory.class)
public interface TenantServiceApiClient extends TenantServiceApi {
}
