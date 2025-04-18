package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteMatchmakerOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerOperationTestInterface deleteMatchmakerOperation;

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Test
    void givenMatchmaker_whenExecute_thenDeleted() {
        final var slot = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId(), MatchmakerConfigDto.create());
        upsertMatchmakerOperation.upsertMatchmaker(slot, matchmaker);

        final var changeContext = deleteMatchmakerOperation.deleteMatchmaker(slot, matchmaker.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_DELETED));
    }

    @Test
    void givenUnknownUuid_whenExecute_thenSkip() {
        final var slot = 0;
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchmakerOperation.deleteMatchmaker(slot, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}