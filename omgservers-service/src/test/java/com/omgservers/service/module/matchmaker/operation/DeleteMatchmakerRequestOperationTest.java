package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.request.MatchmakerRequestConfigDto;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerRequestModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.DeleteMatchmakerRequestOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerRequestOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteMatchmakerRequestOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerRequestOperationTestInterface deleteRequestOperation;

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerRequestOperationTestInterface upsertMatchmakerRequestOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerRequestModelFactory matchmakerRequestModelFactory;

    @Test
    void givenRequest_whenDeleteRequest_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerRequestConfig = MatchmakerRequestConfigDto.create();
        final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmaker.getId(),
                userId(),
                clientId(),
                modeName(),
                matchmakerRequestConfig);
        upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest);

        final var changeContext = deleteRequestOperation
                .deleteMatchmakerRequest(shard, matchmaker.getId(), matchmakerRequest.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_REQUEST_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteRequest_thenSkip() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRequestOperation.deleteMatchmakerRequest(shard, matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_REQUEST_DELETED));
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

    Long versionId() {
        return generateIdOperation.generateId();
    }

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}