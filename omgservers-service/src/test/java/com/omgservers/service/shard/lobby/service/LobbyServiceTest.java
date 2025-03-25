package com.omgservers.service.shard.lobby.service;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.lobby.service.testInterface.LobbyServiceTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class LobbyServiceTest extends BaseTestClass {

    @Inject
    LobbyServiceTestInterface lobbyService;
}