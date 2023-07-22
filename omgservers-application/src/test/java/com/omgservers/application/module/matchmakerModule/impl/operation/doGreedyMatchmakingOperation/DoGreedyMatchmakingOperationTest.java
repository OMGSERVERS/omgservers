package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.versionModule.model.VersionGroupModel;
import com.omgservers.application.module.versionModule.model.VersionModeModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@QuarkusTest
class DoGreedyMatchmakingOperationTest extends Assertions {

    @Inject
    DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;

    @Test
    void testDoGreedyMatchmaking() {
        final var mode = "teams";
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();

        final var modeConfig = VersionModeModel.create(mode, 3, 6, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 2, 4));
            add(VersionGroupModel.create("blue", 1, 2));
        }});

        final var activeRequests = new ArrayList<RequestModel>() {{
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));

            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));

            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        }};

        final var result = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                tenant,
                stage,
                versionUuid(),
                matchmaker,
                modeConfig,
                activeRequests,
                new ArrayList<>());

        assertEquals(activeRequests.size(), result.matchedRequests().size());
        assertTrue(result.failedRequests().isEmpty());
        assertEquals(3, result.preparedMatches().size());
        // match 1
        assertEquals(4, result.preparedMatches().get(0).getConfig().getGroups().get(0).getRequests().size());
        assertEquals(2, result.preparedMatches().get(0).getConfig().getGroups().get(1).getRequests().size());
        // match 2
        assertEquals(4, result.preparedMatches().get(1).getConfig().getGroups().get(0).getRequests().size());
        assertEquals(2, result.preparedMatches().get(1).getConfig().getGroups().get(1).getRequests().size());
        // match 3
        assertEquals(3, result.preparedMatches().get(2).getConfig().getGroups().get(0).getRequests().size());
        assertEquals(2, result.preparedMatches().get(2).getConfig().getGroups().get(1).getRequests().size());
    }

    UUID matchmakerUuid() {
        return UUID.randomUUID();
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }

    UUID versionUuid() {
        return UUID.randomUUID();
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID clientUuid() {
        return UUID.randomUUID();
    }
}