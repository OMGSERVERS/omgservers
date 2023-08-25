package com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation;

import com.omgservers.base.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface UserServiceApiClient extends UserServiceApi {
}
