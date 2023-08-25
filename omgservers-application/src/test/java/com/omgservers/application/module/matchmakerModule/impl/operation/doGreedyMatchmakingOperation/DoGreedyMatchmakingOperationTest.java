package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.base.factory.RequestModelFactory;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
@QuarkusTest
class DoGreedyMatchmakingOperationTest extends Assertions {

    @Inject
    DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;

    @Inject
    RequestModelFactory requestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void testDoGreedyMatchmaking() {
        final var mode = "teams";
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();

        final var modeConfig = VersionModeModel.create(mode, 3, 6, new ArrayList<>() {{
            add(VersionGroupModel.create("red", 2, 4));
            add(VersionGroupModel.create("blue", 1, 2));
        }});

        final var activeRequests = new ArrayList<RequestModel>() {{
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));

            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));

            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        }};

        final var result = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                tenant,
                stage,
                versionId(),
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

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }
}