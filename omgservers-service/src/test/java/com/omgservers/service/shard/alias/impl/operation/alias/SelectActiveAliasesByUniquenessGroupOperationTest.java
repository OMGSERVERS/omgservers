package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.alias.impl.operation.alias.testInterface.SelectActiveAliasesByUniquenessGroupOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectActiveAliasesByUniquenessGroupOperationTest extends BaseTestClass {

    @Inject
    SelectActiveAliasesByUniquenessGroupOperationTestInterface selectActiveAliasesByUniquenessGroupOperation;

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

        final var aliases = selectActiveAliasesByUniquenessGroupOperation
                .execute(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                        DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP).stream()
                .map(AliasModel::getId)
                .toList();

        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenUnknownUniquenessGroup_whenExecute_thenEmptyResult() {
        final var aliases = selectActiveAliasesByUniquenessGroupOperation
                .execute(generateIdOperation.generateId(), generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}