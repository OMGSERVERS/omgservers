package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchConfigModel;
import com.omgservers.application.module.matchmakerModule.model.match.MatchGroupModel;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.versionModule.model.VersionGroupModel;
import com.omgservers.application.module.versionModule.model.VersionModeModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

@QuarkusTest
class DoGreedyMatchmakingOperationImplTest extends Assertions {

    @Inject
    DoGreedyMatchmakingOperationImpl doGreedyMatchmakingOperation;

    @Test
    void testMatchRequestWithMatch() {
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 2);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionUuid(), versionMode);
        final var match = MatchModel.create(matchmaker, matchConfig);

        final var request1 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));
        final var request2 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));
        final var request3 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));
        final var request4 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));

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
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();
        final var mode = "mode";

        final var versionGroup = VersionGroupModel.create("group", 1, 1);

        final var request1 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));
        final var request2 = RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode));
        final var matchGroup = MatchGroupModel.create(versionGroup);

        assertTrue(doGreedyMatchmakingOperation.matchRequestWithGroup(request1, matchGroup));
        assertEquals(1, matchGroup.getRequests().size());
        assertFalse(doGreedyMatchmakingOperation.matchRequestWithGroup(request2, matchGroup));
        assertEquals(1, matchGroup.getRequests().size());
    }

    @Test
    void testCountMatchRequests() {
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 3);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionUuid(), versionMode);
        final var match = MatchModel.create(matchmaker, matchConfig);

        assertEquals(0, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(0).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        matchConfig.getGroups().get(1).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertEquals(2, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(0).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertEquals(3, doGreedyMatchmakingOperation.countMatchRequests(match));

        matchConfig.getGroups().get(1).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertEquals(4, doGreedyMatchmakingOperation.countMatchRequests(match));
    }

    @Test
    void testCheckMatchReadiness() {
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();
        final var mode = "mode";

        final var versionGroup1 = VersionGroupModel.create("group1", 2, 3);
        final var versionGroup2 = VersionGroupModel.create("group2", 1, 1);
        final var versionMode = VersionModeModel.create(mode, 3, 4, new ArrayList<>() {{
            add(versionGroup1);
            add(versionGroup2);
        }});

        final var matchConfig = MatchConfigModel.create(tenant, stage, versionUuid(), versionMode);
        final var match = MatchModel.create(matchmaker, matchConfig);

        matchConfig.getGroups().get(0).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        matchConfig.getGroups().get(1).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertFalse(doGreedyMatchmakingOperation.checkMatchReadiness(match));

        matchConfig.getGroups().get(0).getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertTrue(doGreedyMatchmakingOperation.checkMatchReadiness(match));
    }

    @Test
    void testCheckGroupReadiness() {
        final var matchmaker = matchmakerUuid();
        final var tenant = tenantUuid();
        final var stage = stageUuid();
        final var versionGroup = VersionGroupModel.create("group", 2, 3);
        final var mode = "mode";

        final var matchGroup = MatchGroupModel.create(versionGroup, new ArrayList<>() {{
            add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        }});
        assertFalse(doGreedyMatchmakingOperation.checkGroupReadiness(matchGroup));

        matchGroup.getRequests().add(RequestModel.create(matchmaker, RequestConfigModel.create(userUuid(), clientUuid(), tenant, stage, mode)));
        assertTrue(doGreedyMatchmakingOperation.checkGroupReadiness(matchGroup));
    }

    UUID matchmakerUuid() {
        return UUID.randomUUID();
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID clientUuid() {
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
}