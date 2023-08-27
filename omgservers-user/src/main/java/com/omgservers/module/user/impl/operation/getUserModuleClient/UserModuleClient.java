package com.omgservers.module.user.impl.operation.getUserModuleClient;

import com.omgservers.exception.ClientSideExceptionMapper;
import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.module.user.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface UserModuleClient extends UserServiceApi {
}
