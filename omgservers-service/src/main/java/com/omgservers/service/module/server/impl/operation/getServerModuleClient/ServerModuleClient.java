package com.omgservers.service.module.server.impl.operation.getServerModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.server.impl.service.webService.impl.api.ServeApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
public interface ServerModuleClient extends ServeApi {
}
