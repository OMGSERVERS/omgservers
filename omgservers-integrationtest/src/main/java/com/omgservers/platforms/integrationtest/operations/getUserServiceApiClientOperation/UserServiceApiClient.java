package com.omgservers.platforms.integrationtest.operations.getUserServiceApiClientOperation;

import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.platforms.integrationtest.security.ServiceAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(ServiceAccountCredentialsHeadersFactory.class)
public interface UserServiceApiClient extends UserServiceApi {
}
