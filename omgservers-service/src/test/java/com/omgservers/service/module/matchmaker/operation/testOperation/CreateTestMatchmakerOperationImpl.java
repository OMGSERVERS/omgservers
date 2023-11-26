package com.omgservers.service.module.matchmaker.operation.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.factory.VersionMatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionMatchmakerOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestMatchmakerOperationImpl implements CreateTestMatchmakerOperation {

    final UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;
    final UpsertVersionMatchmakerOperationTestInterface upsertVersionMatchmaker;

    final MatchmakerModelFactory matchmakerModelFactory;
    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;

    @Override
    public TestMatchmakerHolder createTestMatchmaker(final Long tenantId, final Long versionId) {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId, versionId);
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var versionMatchmaker = versionMatchmakerModelFactory.create(tenantId, versionId, matchmaker.getId());
        upsertVersionMatchmaker.upsertVersionMatchmaker(shard, versionMatchmaker);

        return new TestMatchmakerHolder(matchmaker, versionMatchmaker);
    }

    public record TestMatchmakerHolder(
            MatchmakerModel matchmaker,
            VersionMatchmakerModel versionMatchmaker) {
    }
}
