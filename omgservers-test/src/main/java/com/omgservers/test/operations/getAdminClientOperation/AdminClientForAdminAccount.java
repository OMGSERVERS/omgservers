package com.omgservers.test.operations.getAdminClientOperation;

import com.omgservers.test.security.AdminAccountCredentialsHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

@RegisterClientHeaders(AdminAccountCredentialsHeadersFactory.class)
public interface AdminClientForAdminAccount extends AdminApi {
}
