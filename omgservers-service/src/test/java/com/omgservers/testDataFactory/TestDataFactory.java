package com.omgservers.testDataFactory;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ApplicationScoped
@AllArgsConstructor
public class TestDataFactory {

    final MatchmakerTestDataFactory matchmakerTestDataFactory;
    final RuntimeTestDataFactory runtimeTestDataFactory;
    final TenantTestDataFactory tenantTestDataFactory;
    final ClientTestDataFactory clientTestDataFactory;
    final LobbyTestDataFactory lobbyTestDataFactory;
    final RootTestDataFactory rootTestDataFactory;
    final PoolTestDataFactory poolTestDataFactory;
    final UserTestDataFactory userTestDataFactory;
}
