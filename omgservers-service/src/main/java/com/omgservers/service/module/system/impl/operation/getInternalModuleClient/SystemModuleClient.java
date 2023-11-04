package com.omgservers.service.module.system.impl.operation.getInternalModuleClient;

import com.omgservers.service.module.system.impl.service.webService.impl.api.SystemApi;
import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface SystemModuleClient extends SystemApi {
}
