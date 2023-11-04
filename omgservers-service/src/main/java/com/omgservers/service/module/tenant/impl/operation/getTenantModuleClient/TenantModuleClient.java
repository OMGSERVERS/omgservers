package com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient;

import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import com.omgservers.service.module.tenant.impl.service.webService.impl.api.TenantApi;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface TenantModuleClient extends TenantApi {
}
