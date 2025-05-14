package com.omgservers.service.operation.security;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Slf4j
@QuarkusTest
class GetX5COperationTest extends BaseTestClass {

    @Inject
    IssueJwtTokenOperation issueJwtTokenOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void whenIssueServiceJwtToken_thenIssued() {
        final var jwtToken = issueJwtTokenOperation.issueServiceJwtToken();
        assertNotNull(jwtToken);
        log.info("Service JWT token, {}", jwtToken);
    }

    @Test
    void whenIssueUserJwtToken_thenIssued() {
        final var userId = generateIdOperation.generateId();
        final var groups = Set.of(UserRoleEnum.PLAYER.getName());
        final var jwtToken = issueJwtTokenOperation.issueUserJwtToken(userId, groups);
        assertNotNull(jwtToken);
        log.info("User JWT token, {}", jwtToken);
    }
}