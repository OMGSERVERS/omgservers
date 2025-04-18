package com.omgservers.service.shard.alias.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.operation.testInterface.UpsertAliasOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertAliasOperationTest extends BaseTestClass {

    @Inject
    UpsertAliasOperationTestInterface upsertAliasOperation;

    @Inject
    AliasModelFactory aliasModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenAlias_whenUpsertAlias_thenInserted() {
        final var shaslotd = 0;
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");

        final var changeContext = upsertAliasOperation.execute(shaslotd, alias);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenAlias_whenUpsertAlias_thenUpdated() {
        final var slot = 0;
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        upsertAliasOperation.execute(slot, alias);

        final var changeContext = upsertAliasOperation.execute(slot, alias);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenAlias_whenUpsertAlias_thenIdempotencyViolation() {
        final var slot = 0;
        final var alias1 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias");
        upsertAliasOperation.execute(slot, alias1);

        final var alias2 = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                "alias",
                alias1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertAliasOperation.execute(slot, alias2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}