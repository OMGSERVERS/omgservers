package com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient;

import com.omgservers.service.module.runtime.impl.service.webService.impl.api.RuntimeApi;
import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface RuntimeModuleClient extends RuntimeApi {
}
