package com.omgservers.service.module.root.impl.operation.getRootModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.root.impl.service.webService.impl.api.RootApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
public interface RootModuleClient extends RootApi {
}
