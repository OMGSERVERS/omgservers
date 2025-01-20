package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.impl.operation.alias.testInterface.DeleteAliasOperationTestInterface;
import com.omgservers.service.shard.alias.impl.operation.alias.testInterface.UpsertAliasOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
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
        final var shard = 0;
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        upsertAliasOperation.execute(shard, alias);

        final var changeContext = deleteAliasOperation.execute(shard, alias.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenDeleteAlias_thenSkip() {
        final var shard = 0;

        final var changeContext = deleteAliasOperation.execute(shard,
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
    }
}