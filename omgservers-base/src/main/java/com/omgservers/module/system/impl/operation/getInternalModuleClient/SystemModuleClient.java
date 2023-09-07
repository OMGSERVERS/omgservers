package com.omgservers.module.system.impl.operation.getInternalModuleClient;

import com.omgservers.module.system.impl.service.webService.impl.api.SystemApi;
import com.omgservers.security.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface SystemModuleClient extends SystemApi {
}
