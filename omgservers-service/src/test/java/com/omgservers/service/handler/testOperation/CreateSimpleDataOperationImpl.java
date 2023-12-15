package com.omgservers.service.handler.testOperation;

import com.omgservers.service.module.matchmaker.operation.testOperation.CreateTestMatchmakerOperation;
import com.omgservers.service.module.runtime.operation.testOperation.CreateTestRuntimeClientOperation;
import com.omgservers.service.module.runtime.operation.testOperation.CreateTestRuntimeOperation;
import com.omgservers.service.module.tenant.operation.testOperation.CreateTestVersionMatchmakerOperation;
import com.omgservers.service.module.tenant.operation.testOperation.CreateTestVersionOperation;
import com.omgservers.service.module.tenant.operation.testOperation.CreateTestVersionRuntimeOperation;
import com.omgservers.service.module.user.operation.testOperation.CreateTestClientOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateSimpleDataOperationImpl implements CreateSimpleDataOperation {

    @Inject
    CreateTestVersionOperation createTestVersionOperation;

    @Inject
    CreateTestMatchmakerOperation createTestMatchmakerOperation;

    @Inject
    CreateTestVersionMatchmakerOperation createTestVersionMatchmakerOperation;

    @Inject
    CreateTestRuntimeOperation createTestRuntimeOperation;

    @Inject
    CreateTestVersionRuntimeOperation createTestVersionRuntimeOperation;

    @Inject
    CreateTestRuntimeClientOperation createTestRuntimeClientOperation;

    @Inject
    CreateTestClientOperation createTestClientOperation;

    @Override
    public SimpleDataHolder createSimpleData() {
        final var testVersionHolder = createTestVersionOperation.createTestVersion();

        final var testRuntime = createTestRuntimeOperation.createTestRuntime(
                testVersionHolder.tenant().getId(),
                testVersionHolder.version().getId());

        final var testVersionRuntime = createTestVersionRuntimeOperation.createTestVersionRuntime(
                testVersionHolder.tenant().getId(),
                testVersionHolder.version().getId(),
                testRuntime.getId());

        final var testMatchmaker = createTestMatchmakerOperation.createTestMatchmaker(
                testVersionHolder.tenant().getId(),
                testVersionHolder.version().getId());

        final var testVersionMatchmaker = createTestVersionMatchmakerOperation.createTestVersionMatchmaker(
                testVersionHolder.tenant().getId(),
                testVersionHolder.version().getId(),
                testMatchmaker.getId());

        final var testClientHolder = createTestClientOperation.createTestClient(
                testVersionHolder.tenant().getId(),
                testVersionHolder.stage().getId(),
                testVersionHolder.version().getId(),
                testMatchmaker.getId(),
                testRuntime.getId());

        final var testRuntimeClient = createTestRuntimeClientOperation.createTestRuntimeClientOperation(
                testRuntime.getId(),
                testClientHolder.client().getUserId(),
                testClientHolder.client().getId());

        return new SimpleDataHolder(
                testVersionHolder,
                testRuntime,
                testVersionRuntime,
                testMatchmaker,
                testVersionMatchmaker,
                testClientHolder,
                testRuntimeClient);
    }
}
