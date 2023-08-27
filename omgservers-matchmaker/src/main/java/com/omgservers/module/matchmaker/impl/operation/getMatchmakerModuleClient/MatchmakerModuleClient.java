package com.omgservers.module.matchmaker.impl.operation.getMatchmakerModuleClient;

import com.omgservers.exception.ClientSideExceptionMapper;
import com.omgservers.module.matchmaker.impl.service.matchmakerWebService.impl.serviceApi.MatchmakerServiceApi;
import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface MatchmakerModuleClient extends MatchmakerServiceApi {
}
