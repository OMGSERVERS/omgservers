package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.AdminClient;

import java.net.URI;

public interface CreateAdminClientOperation {
    AdminClient execute(URI uri, String bearerToken);
}
