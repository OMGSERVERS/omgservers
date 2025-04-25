package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.AdminClient;

import java.net.URI;

public interface CreateAdminAnonymousClientOperation {
    AdminClient execute(URI uri);
}
