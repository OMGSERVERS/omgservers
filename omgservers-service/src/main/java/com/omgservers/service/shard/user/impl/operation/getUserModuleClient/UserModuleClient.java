package com.omgservers.service.shard.user.impl.operation.getUserModuleClient;

import com.omgservers.service.security.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.shard.user.impl.service.webService.impl.serviceApi.UserApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface UserModuleClient extends UserApi {
}
