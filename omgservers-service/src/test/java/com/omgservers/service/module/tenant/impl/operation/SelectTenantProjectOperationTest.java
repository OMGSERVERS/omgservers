package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.SelectProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantProjectOperationTest extends Assertions {

    @Inject
    SelectProjectOperationTestInterface selectProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProject_whenExecute_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var project1 = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project1);

        final var project2 = selectProjectOperation.selectProject(shard, tenant.getId(), project1.getId(), false);
        assertEquals(project1, project2);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();
        final var tenantId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectProjectOperation
                .selectProject(shard, id, tenantId, false));
    }
}