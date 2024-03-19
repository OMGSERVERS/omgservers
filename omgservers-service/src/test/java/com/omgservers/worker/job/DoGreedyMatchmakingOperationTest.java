package com.omgservers.worker.job;

import com.omgservers.service.factory.MatchmakerRequestModelFactory;
import com.omgservers.service.handler.job.matchmaker.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class DoGreedyMatchmakingOperationTest extends Assertions {

    @Inject
    DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;

    @Inject
    MatchmakerRequestModelFactory matchmakerRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

//    @Test
//    void testDoGreedyMatchmaking() {
//        final var mode = "2team";
//        final var matchmaker = matchmakerId();
//        final var tenant = tenantId();
//        final var stage = stageId();
//
//        final var modeConfig = VersionModeModel.create(mode, 3, 6, new ArrayList<>() {{
//            add(VersionGroupModel.create("red", 2, 4));
//            add(VersionGroupModel.create("blue", 1, 2));
//        }});
//
//        final var matchmakerRequests = new ArrayList<MatchmakerRequestModel>() {{
//            // match 1, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode, MatchmakerRequestConfigModel.create(
//                    PlayerAttributesModel.create())));
//            // match 1, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 1, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 1, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 1, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 1, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//
//            // match 2, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 2, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 2, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 2, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 2, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 2, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//
//            // match 3, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 3, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 3, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 3, group - blue
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//            // match 3, group - red
//            add(matchmakerRequestModelFactory.create(matchmaker, userId(), clientId(), mode,
//                    MatchmakerRequestConfigModel.create(PlayerAttributesModel.create())));
//        }};
//
//        final var result = doGreedyMatchmakingOperation.doGreedyMatchmaking(
//                tenant,
//                stage,
//                versionId(),
//                matchmaker,
//                modeConfig,
//                matchmakerRequests,
//                new ArrayList<>());
//
//        assertEquals(3, result.createdMatches().size());
//        assertTrue(result.updatedMatches().isEmpty());
//        assertEquals(matchmakerRequests.size(), result.completedRequests().size());
//
//        // match 1
//        assertEquals(4, result.createdMatches().get(0).getConfig().getGroups().get(0).getRequests().size());
//        assertEquals(2, result.createdMatches().get(0).getConfig().getGroups().get(1).getRequests().size());
//        // match 2
//        assertEquals(4, result.createdMatches().get(1).getConfig().getGroups().get(0).getRequests().size());
//        assertEquals(2, result.createdMatches().get(1).getConfig().getGroups().get(1).getRequests().size());
//        // match 3
//        assertEquals(3, result.createdMatches().get(2).getConfig().getGroups().get(0).getRequests().size());
//        assertEquals(2, result.createdMatches().get(2).getConfig().getGroups().get(1).getRequests().size());
//    }
//
//    Long matchmakerId() {
//        return generateIdOperation.generateId();
//    }
//
//    Long tenantId() {
//        return generateIdOperation.generateId();
//    }
//
//    Long stageId() {
//        return generateIdOperation.generateId();
//    }
//
//    Long versionId() {
//        return generateIdOperation.generateId();
//    }
//
//    Long userId() {
//        return generateIdOperation.generateId();
//    }
//
//    Long clientId() {
//        return generateIdOperation.generateId();
//    }

}