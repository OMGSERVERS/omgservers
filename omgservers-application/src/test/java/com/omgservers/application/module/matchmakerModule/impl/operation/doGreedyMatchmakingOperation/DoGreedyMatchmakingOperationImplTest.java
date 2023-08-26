package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchGroupModel;
import com.omgservers.application.factory.MatchModelFactory;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.application.factory.RequestModelFactory;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@QuarkusTest
class DoGreedyMatchmakingOperationImplTest extends Assertions {

    @Inject
    DoGreedyMatchmakingOperationImpl doGreedyMatchmakingOperation;

    @Inject
    RequestModelFactory requestModelFactory;

    @Inject
    MatchModelFactory matchModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void testMatchRequestWithMatch() {
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 2);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionId(), versionMode);
        final var match = matchModelFactory.create(matchmaker, runtimeId(), matchConfig);

        final var request1 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));
        final var request2 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));
        final var request3 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));
        final var request4 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));

        assertTrue(doGreedyMatchmakingOperation.matchRequestWithMatch(request1, match));
        assertTrue(match.getConfig().getGroups().get(0).getRequests().contains(request1));

        assertTrue(doGreedyMatchmakingOperation.matchRequestWithMatch(request2, match));
        assertTrue(match.getConfig().getGroups().get(1).getRequests().contains(request2));

        assertTrue(doGreedyMatchmakingOperation.matchRequestWithMatch(request3, match));
        assertTrue(match.getConfig().getGroups().get(0).getRequests().contains(request3));

        assertFalse(doGreedyMatchmakingOperation.matchRequestWithMatch(request4, match));
    }

    @Test
    void testMatchRequestWithGroup() {
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();
        final var mode = "mode";

        final var versionGroup = VersionGroupModel.create("group", 1, 1);

        final var request1 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));
        final var request2 = requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode));
        final var matchGroup = MatchGroupModel.create(versionGroup);

        assertTrue(doGreedyMatchmakingOperation.matchRequestWithGroup(request1, matchGroup));
        assertEquals(1, matchGroup.getRequests().size());
        assertFalse(doGreedyMatchmakingOperation.matchRequestWithGroup(request2, matchGroup));
        assertEquals(1, matchGroup.getRequests().size());
    }

    @Test
    void testCountMatchRequests() {
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 3);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionId(), versionMode);
        final var match = matchModelFactory.create(matchmaker, runtimeId(), matchConfig);

        assertEquals(0, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(0).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        matchConfig.getGroups().get(1).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertEquals(2, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(0).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertEquals(3, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(1).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertEquals(4, doGreedyMatchmakingOperation.countMatchRequests(match));
    }

    @Test
    void testCheckMatchReadiness() {
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 3);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionId(), versionMode);
        final var match = matchModelFactory.create(matchmaker, runtimeId(), matchConfig);

        matchConfig.getGroups().get(0).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        matchConfig.getGroups().get(1).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertFalse(doGreedyMatchmakingOperation.checkMatchReadiness(match));

        matchConfig.getGroups().get(0).getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertTrue(doGreedyMatchmakingOperation.checkMatchReadiness(match));
    }

    @Test
    void testCheckGroupReadiness() {
        final var matchmaker = matchmakerId();
        final var tenant = tenantId();
        final var stage = stageId();
        final var versionGroup = VersionGroupModel.create("group", 2, 3);
        final var mode = "mode";

        final var matchGroup = MatchGroupModel.create(versionGroup, new ArrayList<>() {{
            add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        }});
        assertFalse(doGreedyMatchmakingOperation.checkGroupReadiness(matchGroup));

        matchGroup.getRequests().add(requestModelFactory.create(matchmaker, userId(), clientId(), RequestConfigModel.create(mode)));
        assertTrue(doGreedyMatchmakingOperation.checkGroupReadiness(matchGroup));
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
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
}