package com.omgservers.service.module.user.impl.operation.getUserModuleClient;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.security.ServiceAccountClientHeadersFactory;
import com.omgservers.service.module.user.impl.service.webService.impl.serviceApi.UserApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface UserModuleClient extends UserApi {
}
