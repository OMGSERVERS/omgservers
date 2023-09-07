package com.omgservers.module.matchmaker.impl.operation.getMatchmakerModuleClient;

import com.omgservers.exception.ClientSideExceptionMapper;
import com.omgservers.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import com.omgservers.security.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface MatchmakerModuleClient extends MatchmakerApi {
}
