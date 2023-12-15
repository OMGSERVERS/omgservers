package com.omgservers.service.module.matchmaker.operation.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestMatchmakerOperationImpl implements CreateTestMatchmakerOperation {

    final UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public MatchmakerModel createTestMatchmaker(final Long tenantId, final Long versionId) {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId, versionId);
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        return matchmaker;
    }
}
