package com.omgservers.service.shard.alias.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.alias.operation.testInterface.SelectActiveAliasesByUniquenessGroupOperationTestInterface;
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
    void givenAliases_whenExecute_thenSelected() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();

        final var project1 = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var alias1 = testDataFactory.getAliasTestDataFactory().createAlias(project1,
                "project-" + UUID.randomUUID());

        final var project2 = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var alias2 = testDataFactory.getAliasTestDataFactory().createAlias(project2,
                "project-" + UUID.randomUUID());

        final var shardKey = tenant.getId().toString();
        final var uniquenessGroup = tenant.getId();

        final var aliases = selectActiveAliasesByUniquenessGroupOperation.execute(shardKey, uniquenessGroup).stream()
                .map(AliasModel::getId)
                .toList();

        assertTrue(aliases.contains(alias1.getId()));
        assertTrue(aliases.contains(alias2.getId()));
    }

    @Test
    void givenUnknownUniquenessGroup_whenExecute_thenEmptyResult() {
        final var aliases = selectActiveAliasesByUniquenessGroupOperation
                .execute(generateIdOperation.generateStringId(), generateIdOperation.generateId());
        assertTrue(aliases.isEmpty());
    }
}