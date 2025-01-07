package com.omgservers.tester.operation.bootstrapTestClient;

import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.dto.TestClientDto;
import com.omgservers.tester.dto.TestVersionDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BootstrapTestClientOperationImpl implements BootstrapTestClientOperation {
    static final AtomicLong idGenerator = new AtomicLong();

    final PlayerApiTester playerApiTester;

    @Override
    public TestClientDto bootstrapTestClient(final TestVersionDto testVersion) throws IOException {
        final var createUserResponse = playerApiTester.createUser();
        final var userId = createUserResponse.getUserId();
        final var password = createUserResponse.getPassword();

        return createTestClient(testVersion, userId, password);
    }

    @Override
    public TestClientDto bootstrapTestClient(final TestVersionDto testVersion,
                                             final TestClientDto testClient)
            throws IOException {

        final var userId = testClient.getUserId();
        final var password = testClient.getPassword();

        return createTestClient(testVersion, userId, password);
    }

    TestClientDto createTestClient(final TestVersionDto testVersion,
                                   final Long userId,
                                   final String password) throws IOException {
        final var token = playerApiTester.createToken(userId, password);

        final var clientId = playerApiTester.createClient(token,
                testVersion.getTenantId().toString(),
                testVersion.getTenantProjectId().toString(),
                testVersion.getTenantStageId().toString());

        return TestClientDto.builder()
                .id(idGenerator.getAndIncrement())
                .userId(userId)
                .password(password)
                .rawToken(token)
                .clientId(clientId)
                .build();
    }
}
