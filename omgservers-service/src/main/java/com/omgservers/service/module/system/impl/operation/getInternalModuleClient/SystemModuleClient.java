package com.omgservers.service.module.system.impl.operation.getInternalModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.system.impl.service.webService.impl.api.SystemApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
public interface SystemModuleClient extends SystemApi {
}
