package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.factory.VersionLobbyRefModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionRuntimeOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestVersionRuntimeOperationImpl implements CreateTestVersionRuntimeOperation {

    final UpsertVersionRuntimeOperationTestInterface upsertVersionRuntimeOperation;

    final VersionLobbyRefModelFactory versionLobbyRefModelFactory;

    @Override
    public VersionLobbyRefModel createTestVersionRuntime(final Long tenantId,
                                                         final Long versionId,
                                                         final Long runtimeId) {
        int shard = 0;

        final var versionRuntime = versionLobbyRefModelFactory.create(tenantId, versionId, runtimeId);
        upsertVersionRuntimeOperation.upsertVersionRuntime(shard, versionRuntime);

        return versionRuntime;
    }
}
