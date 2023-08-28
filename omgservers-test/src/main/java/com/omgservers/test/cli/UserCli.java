package com.omgservers.test.cli;

import com.omgservers.test.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.test.operations.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.test.operations.getUserServiceApiClientOperation.UserServiceApiClient;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class UserCli {

    final GetConfigOperation getConfigOperation;
    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;

    UserServiceApiClient client;

    public void createClient() {
        final var uri = getConfigOperation.getServers().get(0).externalAddress();
        createClient(uri);
    }

    public void createClient(URI uri) {
        client = getUserServiceApiClientOperation.getClient(uri);
    }

    public UserServiceApiClient getClient() {
        return client;
    }
}
