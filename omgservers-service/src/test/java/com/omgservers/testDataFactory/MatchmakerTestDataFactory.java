package com.omgservers.testDataFactory;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerTestDataFactory {

    final MatchmakerServiceTestInterface matchmakerService;

    final MatchmakerModelFactory matchmakerModelFactory;

    public MatchmakerModel createMatchmaker(final TenantModel tenant,
                                            final VersionModel version) {
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var matchmaker = matchmakerModelFactory.create(tenantId, versionId);
        final var syncMatchmakerRequest = new SyncMatchmakerRequest(matchmaker);
        matchmakerService.syncMatchmaker(syncMatchmakerRequest);
        return matchmaker;
    }
}
