package com.omgservers.service.shard.alias.impl.operation.alias;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.shard.alias.impl.operation.alias.testInterface.SelectAliasesByUniquenessGroupOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class SelectAliasesByUniquenessGroupOperationTest extends BaseTestClass {

    @Inject
    SelectAliasesByUniquenessGroupOperationTestInterface selectAliasesByUniquenessGroupOperation;

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

        final var aliases = selectAliasesByUniquenessGroupOperation
                .execute(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                        DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP).stream()
                .map(AliasModel::getId)
                .toList();

        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenUnknownUniquenessGroup_whenExecute_thenEmptyResult() {
        final var aliases = selectAliasesByUniquenessGroupOperation
                .execute(generateIdOperation.generateId(), generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}