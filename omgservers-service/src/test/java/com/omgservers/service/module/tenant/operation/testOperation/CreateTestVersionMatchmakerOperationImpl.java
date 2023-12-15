package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.factory.VersionMatchmakerModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionMatchmakerOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestVersionMatchmakerOperationImpl implements CreateTestVersionMatchmakerOperation {

    final UpsertVersionMatchmakerOperationTestInterface upsertVersionMatchmakerOperation;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;

    @Override
    public VersionMatchmakerModel createTestVersionMatchmaker(final Long tenantId,
                                                              final Long versionId,
                                                              final Long matchmakerId) {
        int shard = 0;

        final var versionMatchmaker = versionMatchmakerModelFactory
                .create(tenantId, versionId, matchmakerId);
        upsertVersionMatchmakerOperation.upsertVersionMatchmaker(shard, versionMatchmaker);

        return versionMatchmaker;
    }
}
