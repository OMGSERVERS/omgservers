package com.omgservers.module.tenant.impl.operation.getTenantModuleClient;

import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.module.tenant.impl.service.tenantWebService.impl.serviceApi.TenantServiceApi;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface TenantModuleClient extends TenantServiceApi {
}
