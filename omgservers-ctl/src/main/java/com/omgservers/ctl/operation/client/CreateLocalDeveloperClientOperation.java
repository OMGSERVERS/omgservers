package com.omgservers.ctl.operation.client;

import com.omgservers.ctl.client.DeveloperClient;

public interface CreateLocalDeveloperClientOperation {
    DeveloperClient execute(String developer, String password);
}
