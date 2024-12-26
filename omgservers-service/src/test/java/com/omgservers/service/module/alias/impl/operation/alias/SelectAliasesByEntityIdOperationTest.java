package com.omgservers.service.module.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.module.alias.impl.operation.alias.testInterface.SelectAliasesByEntityIdOperationTestInterface;
import com.omgservers.service.module.alias.impl.operation.alias.testInterface.UpsertAliasOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectAliasesByEntityIdOperationTest extends BaseTestClass {

    @Inject
    SelectAliasesByEntityIdOperationTestInterface selectAliasesByEntityIdOperation;

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
        final var entityId = generateIdOperation.generateId();

        final var alias1 = aliasModelFactory.create(shardKey,
                "alias1",
                entityId);
        upsertAliasOperation.execute(shard, alias1);

        final var alias2 = aliasModelFactory.create(shardKey,
                "alias2",
                entityId);
        upsertAliasOperation.execute(shard, alias2);

        final var aliases = selectAliasesByEntityIdOperation
                .execute(shardKey, entityId).stream()
                .map(AliasModel::getId)
                .toList();
        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenAliases_whenExecute_thenEmptyResult() {
        final var aliases = selectAliasesByEntityIdOperation.execute(generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}