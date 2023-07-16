package com.omgservers.platforms.integrationtest.operations.getTenantServiceApiClientOperation;

import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi.TenantServiceApi;
import com.omgservers.platforms.integrationtest.security.ServiceAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(ServiceAccountCredentialsHeadersFactory.class)
public interface TenantServiceApiClient extends TenantServiceApi {
}
