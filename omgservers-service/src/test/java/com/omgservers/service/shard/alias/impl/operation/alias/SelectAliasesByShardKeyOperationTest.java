package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.shard.alias.impl.operation.alias.testInterface.SelectAliasesByShardKeyOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectAliasesByShardKeyOperationTest extends BaseTestClass {

    @Inject
    SelectAliasesByShardKeyOperationTestInterface selectAliasesByShardKeyOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenTenantAliases_whenExecute_thenSelected() {
        final var tenant1 = testDataFactory.getTenantTestDataFactory().createTenant();
        final var alias1 = testDataFactory.getAliasTestDataFactory()
                .createAlias(tenant1, "tenant-" + UUID.randomUUID());

        final var tenant2 = testDataFactory.getTenantTestDataFactory().createTenant();
        final var alias2 = testDataFactory.getAliasTestDataFactory()
                .createAlias(tenant2, "tenant-" + UUID.randomUUID());

        final var aliases = selectAliasesByShardKeyOperation
                .execute(DefaultAliasConfiguration.GLOBAL_SHARD_KEY).stream()
                .map(AliasModel::getId)
                .toList();

        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenUnknownShardKey_whenExecute_thenEmptyResult() {
        final var aliases = selectAliasesByShardKeyOperation.execute(generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}