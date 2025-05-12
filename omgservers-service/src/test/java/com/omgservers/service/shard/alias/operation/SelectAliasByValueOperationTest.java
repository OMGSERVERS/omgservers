package com.omgservers.service.shard.alias.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.alias.operation.testInterface.SelectAliasByValueOperationTestInterface;
import com.omgservers.service.shard.alias.operation.testInterface.UpsertAliasOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectAliasByValueOperationTest extends BaseTestClass {

    @Inject
    SelectAliasByValueOperationTestInterface selectAliasByValueOperation;

    @Inject
    UpsertAliasOperationTestInterface upsertAliasOperation;

    @Inject
    AliasModelFactory aliasModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenAlias_whenExecute_thenSelected() {
        final var slot = 0;
        final var alias1 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                String.valueOf(generateIdOperation.generateId()),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        upsertAliasOperation.execute(slot, alias1);

        final var alias2 = selectAliasByValueOperation.execute(AliasQualifierEnum.TENANT,
                alias1.getShardKey(),
                alias1.getUniquenessGroup(),
                alias1.getValue());
        assertEquals(alias1, alias2);
    }

    @Test
    void givenAlias_whenExecute_thenNotFound() {
        final var exception = assertThrows(ServerSideNotFoundException.class,
                () -> selectAliasByValueOperation.execute(
                        AliasQualifierEnum.TENANT,
                        String.valueOf(generateIdOperation.generateId()),
                        generateIdOperation.generateId(),
                        UUID.randomUUID().toString()));
        assertEquals(ExceptionQualifierEnum.OBJECT_NOT_FOUND, exception.getQualifier());
    }
}