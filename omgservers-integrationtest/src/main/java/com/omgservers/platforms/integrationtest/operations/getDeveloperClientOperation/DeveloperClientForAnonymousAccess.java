package com.omgservers.platforms.integrationtest.operations.getDeveloperClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.developerModule.impl.service.developerWebService.impl.developerApi.DeveloperApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
public interface DeveloperClientForAnonymousAccess extends DeveloperApi {
}
