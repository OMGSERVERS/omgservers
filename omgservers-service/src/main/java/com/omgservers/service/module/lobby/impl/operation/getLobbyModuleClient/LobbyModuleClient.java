package com.omgservers.service.module.lobby.impl.operation.getLobbyModuleClient;

import com.omgservers.service.component.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.lobby.impl.service.webService.impl.api.LobbyApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface LobbyModuleClient extends LobbyApi {
}
