package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.factory.VersionMatchmakerRefModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionMatchmakerOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestVersionMatchmakerOperationImpl implements CreateTestVersionMatchmakerOperation {

    final UpsertVersionMatchmakerOperationTestInterface upsertVersionMatchmakerOperation;

    final VersionMatchmakerRefModelFactory versionMatchmakerRefModelFactory;

    @Override
    public VersionMatchmakerRefModel createTestVersionMatchmaker(final Long tenantId,
                                                                 final Long versionId,
                                                                 final Long matchmakerId) {
        int shard = 0;

        final var versionMatchmaker = versionMatchmakerRefModelFactory
                .create(tenantId, versionId, matchmakerId);
        upsertVersionMatchmakerOperation.upsertVersionMatchmaker(shard, versionMatchmaker);

        return versionMatchmaker;
    }
}
