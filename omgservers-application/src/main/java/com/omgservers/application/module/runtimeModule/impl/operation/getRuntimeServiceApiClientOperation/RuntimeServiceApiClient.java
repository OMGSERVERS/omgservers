package com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi.MatchmakerServiceApi;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl.serviceApi.RuntimeServiceApi;
import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface RuntimeServiceApiClient extends RuntimeServiceApi {
}
