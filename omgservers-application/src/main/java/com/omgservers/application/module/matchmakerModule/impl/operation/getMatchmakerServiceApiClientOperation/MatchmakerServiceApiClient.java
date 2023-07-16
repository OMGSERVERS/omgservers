package com.omgservers.application.module.matchmakerModule.impl.operation.getMatchmakerServiceApiClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerWebService.impl.serviceApi.MatchmakerServiceApi;
import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface MatchmakerServiceApiClient extends MatchmakerServiceApi {
}
