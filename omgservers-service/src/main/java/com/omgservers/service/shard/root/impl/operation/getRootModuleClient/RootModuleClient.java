package com.omgservers.service.shard.root.impl.operation.getRootModuleClient;

import com.omgservers.service.security.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.shard.root.impl.service.webService.impl.api.RootApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface RootModuleClient extends RootApi {
}
