package com.omgservers.service.shard.lobby.impl.service.lobbyService;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class LobbyServiceTest extends BaseTestClass {

    @Inject
    LobbyService lobbyService;
}