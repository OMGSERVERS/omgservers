package com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient;

import com.omgservers.module.runtime.impl.service.runtimeWebService.impl.serviceApi.RuntimeServiceApi;
import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface RuntimeModuleClient extends RuntimeServiceApi {
}
