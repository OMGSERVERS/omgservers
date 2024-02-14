package com.omgservers.service.module.lobby.impl.operation.getLobbyModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.lobby.impl.service.webService.impl.api.LobbyApi;
import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface LobbyModuleClient extends LobbyApi {
}
