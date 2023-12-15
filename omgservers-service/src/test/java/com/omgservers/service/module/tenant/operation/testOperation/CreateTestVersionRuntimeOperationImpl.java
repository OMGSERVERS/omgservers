package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.factory.VersionRuntimeModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionRuntimeOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestVersionRuntimeOperationImpl implements CreateTestVersionRuntimeOperation {

    final UpsertVersionRuntimeOperationTestInterface upsertVersionRuntimeOperation;

    final VersionRuntimeModelFactory versionRuntimeModelFactory;

    @Override
    public VersionRuntimeModel createTestVersionRuntime(final Long tenantId,
                                                        final Long versionId,
                                                        final Long runtimeId) {
        int shard = 0;

        final var versionRuntime = versionRuntimeModelFactory.create(tenantId, versionId, runtimeId);
        upsertVersionRuntimeOperation.upsertVersionRuntime(shard, versionRuntime);

        return versionRuntime;
    }
}
