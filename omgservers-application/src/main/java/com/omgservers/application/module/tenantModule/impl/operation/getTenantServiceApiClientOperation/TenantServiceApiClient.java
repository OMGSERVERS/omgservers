package com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi.TenantServiceApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface TenantServiceApiClient extends TenantServiceApi {
}
