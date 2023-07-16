package com.omgservers.platforms.integrationtest.operations.getAdminClientOperation;

import com.omgservers.application.module.adminModule.impl.service.adminWebService.impl.adminApi.AdminApi;
import com.omgservers.platforms.integrationtest.security.AdminAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(AdminAccountCredentialsHeadersFactory.class)
public interface AdminClientForAdminAccount extends AdminApi {
}
