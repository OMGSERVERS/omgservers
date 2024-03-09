package com.omgservers.tester.operation.bootstrapTestClient;

import com.omgservers.tester.component.PlayerApiTester;
import com.omgservers.tester.model.TestClientModel;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

    @Inject
    PlayerApiTester playerApiTester;

    @Override
    public TestClientModel bootstrapTestClient(final TestVersionModel testVersion) throws IOException {
        final var createUserResponse = playerApiTester.createUser();
        final var userId = createUserResponse.getUserId();
        final var password = createUserResponse.getPassword();

        return createTestClient(testVersion, userId, password);
    }

    @Override
    public TestClientModel bootstrapTestClient(final TestVersionModel testVersion,
                                               final TestClientModel testClient)
            throws IOException {

        final var userId = testClient.getUserId();
        final var password = testClient.getPassword();

        return createTestClient(testVersion, userId, password);
    }

    TestClientModel createTestClient(final TestVersionModel testVersion,
                                     final Long userId,
                                     final String password) throws IOException {
        final var token = playerApiTester.createToken(userId, password);

        final var clientId = playerApiTester.createClient(token,
                testVersion.getTenantId(),
                testVersion.getStageId(),
                testVersion.getStageSecret());

        return TestClientModel.builder()
                .id(idGenerator.getAndIncrement())
                .userId(userId)
                .password(password)
                .rawToken(token)
                .clientId(clientId)
                .build();
    }
}
