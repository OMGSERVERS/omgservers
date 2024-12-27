package com.omgservers.service.module.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.module.alias.impl.operation.alias.testInterface.SelectAliasOperationTestInterface;
import com.omgservers.service.module.alias.impl.operation.alias.testInterface.UpsertAliasOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectAliasOperationTest extends BaseTestClass {

    @Inject
    SelectAliasOperationTestInterface selectAliasOperation;

    @Inject
    UpsertAliasOperationTestInterface upsertAliasOperation;

    @Inject
    AliasModelFactory aliasModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenAlias_whenSelectAlias_thenSelected() {
        final var shard = 0;
        final var alias1 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        final var id = alias1.getId();
        upsertAliasOperation.execute(shard, alias1);

        final var alias2 = selectAliasOperation.execute(shard, id);
        assertEquals(alias1, alias2);
    }

    @Test
    void givenUnknownId_whenSelectAlias_thenException() {
        final var shard = 0;
        assertThrows(ServerSideNotFoundException.class, () -> selectAliasOperation
                .execute(shard, generateIdOperation.generateId()));
    }
}