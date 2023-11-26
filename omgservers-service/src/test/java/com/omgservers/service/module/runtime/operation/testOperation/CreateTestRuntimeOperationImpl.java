package com.omgservers.service.module.runtime.operation.testOperation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.factory.VersionRuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionRuntimeOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestRuntimeOperationImpl implements CreateTestRuntimeOperation {

    final UpsertVersionRuntimeOperationTestInterface upsertVersionRuntime;
    final UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    final VersionRuntimeModelFactory versionRuntimeModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public TestRuntimeHolder createTestLobby(final Long tenantId, final Long versionId) {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId,
                versionId,
                RuntimeQualifierEnum.LOBBY,
                RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var versionRuntime = versionRuntimeModelFactory.create(tenantId, versionId, runtime.getId());
        upsertVersionRuntime.upsertVersionRuntime(shard, versionRuntime);

        return new TestRuntimeHolder(runtime, versionRuntime);
    }

    public record TestRuntimeHolder(
            RuntimeModel runtime,
            VersionRuntimeModel versionRuntime) {
    }
}
