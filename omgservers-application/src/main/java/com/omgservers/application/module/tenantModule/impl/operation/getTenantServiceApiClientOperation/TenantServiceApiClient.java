package com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation;

import com.omgservers.base.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi.TenantServiceApi;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface TenantServiceApiClient extends TenantServiceApi {
}
