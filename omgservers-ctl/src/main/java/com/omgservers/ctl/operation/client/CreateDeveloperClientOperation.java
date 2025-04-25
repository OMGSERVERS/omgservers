package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.DeveloperClient;

import java.net.URI;

public interface CreateDeveloperClientOperation {
    DeveloperClient execute(URI uri, String bearerToken);
}
