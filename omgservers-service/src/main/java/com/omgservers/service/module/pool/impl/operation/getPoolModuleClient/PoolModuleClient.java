package com.omgservers.service.module.pool.impl.operation.getPoolModuleClient;

import com.omgservers.service.server.component.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.pool.impl.service.webService.impl.api.PoolApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface PoolModuleClient extends PoolApi {
}
