package com.omgservers.service.module.matchmaker.impl.operation.getMatchmakerModuleClient;

import com.omgservers.service.server.component.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.matchmaker.impl.service.webService.impl.api.MatchmakerApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface MatchmakerModuleClient extends MatchmakerApi {
}
