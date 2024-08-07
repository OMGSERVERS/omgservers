package com.omgservers.service.module.tenant.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.SelectProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectProjectOperationTest extends Assertions {

    @Inject
    SelectProjectOperationTestInterface selectProjectOperation;

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenProject_whenSelectProject_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);

        final var project1 = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project1);

        final var project2 = selectProjectOperation.selectProject(shard, tenant.getId(), project1.getId(), false);
        assertEquals(project1, project2);
    }

    @Test
    void givenUnknownId_whenSelectProject_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();
        final var tenantId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectProjectOperation
                .selectProject(shard, id, tenantId, false));
    }
}