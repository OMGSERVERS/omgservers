package com.omgservers.service.module.client.impl.operation.getClientModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.client.impl.service.webService.impl.api.ClientApi;
import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface ClientModuleClient extends ClientApi {
}
