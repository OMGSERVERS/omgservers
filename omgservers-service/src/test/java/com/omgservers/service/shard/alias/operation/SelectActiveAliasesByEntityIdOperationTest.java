package com.omgservers.service.shard.alias.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.operation.testInterface.SelectActiveAliasesByEntityIdOperationTestInterface;
import com.omgservers.service.shard.alias.operation.testInterface.UpsertAliasOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectActiveAliasesByEntityIdOperationTest extends BaseTestClass {

    @Inject
    SelectActiveAliasesByEntityIdOperationTestInterface selectActiveAliasesByEntityIdOperation;

    @Inject
    UpsertAliasOperationTestInterface upsertAliasOperation;

    @Inject
    AliasModelFactory aliasModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenAliases_whenExecute_thenSelected() {
        final var shard = 0;
        final var shardKey = generateIdOperation.generateId();
        final var uniquenessGroup = generateIdOperation.generateId();
        final var entityId = generateIdOperation.generateId();

        final var alias1 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                shardKey,
                uniquenessGroup,
                entityId,
                "tenant-" + UUID.randomUUID());
        upsertAliasOperation.execute(shard, alias1);

        final var alias2 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                shardKey,
                uniquenessGroup,
                entityId,
                "tenant-" + UUID.randomUUID());
        upsertAliasOperation.execute(shard, alias2);

        final var aliases = selectActiveAliasesByEntityIdOperation
                .execute(shardKey, entityId).stream()
                .map(AliasModel::getId)
                .toList();
        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenAliases_whenExecute_thenEmptyResult() {
        final var aliases = selectActiveAliasesByEntityIdOperation.execute(generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}