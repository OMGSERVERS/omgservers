package com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi.RuntimeServiceApi;
import com.omgservers.base.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface RuntimeServiceApiClient extends RuntimeServiceApi {
}
