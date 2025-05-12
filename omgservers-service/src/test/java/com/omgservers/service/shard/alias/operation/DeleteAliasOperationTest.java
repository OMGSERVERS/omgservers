package com.omgservers.service.shard.alias.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.alias.operation.testInterface.DeleteAliasOperationTestInterface;
import com.omgservers.service.shard.alias.operation.testInterface.UpsertAliasOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteAliasOperationTest extends BaseTestClass {

    @Inject
    DeleteAliasOperationTestInterface deleteAliasOperation;

    @Inject
    UpsertAliasOperationTestInterface upsertAliasOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    AliasModelFactory aliasModelFactory;

    @Test
    void givenAlias_whenDeleteAlias_thenDeleted() {
        final var slot = 0;
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateStringId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        upsertAliasOperation.execute(slot, alias);

        final var changeContext = deleteAliasOperation.execute(slot, alias.getShardKey(), alias.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.ALIAS_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteAlias_thenSkip() {
        final var slot = 0;

        final var changeContext = deleteAliasOperation.execute(slot,
                String.valueOf(generateIdOperation.generateId()),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }
}