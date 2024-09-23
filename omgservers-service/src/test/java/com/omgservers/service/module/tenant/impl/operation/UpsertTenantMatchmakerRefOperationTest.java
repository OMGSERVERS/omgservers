package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantDeploymentOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantVersionOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertVersionMatchmakerRefOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@QuarkusTest
class UpsertTenantMatchmakerRefOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertTenantVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertTenantDeploymentOperationTestInterface upsertTenantDeploymentOperation;

    @Inject
    UpsertVersionMatchmakerRefOperationTestInterface upsertVersionMatchmakerRefOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    TenantVersionModelFactory tenantVersionModelFactory;

    @Inject
    TenantDeploymentModelFactory tenantDeploymentModelFactory;

    @Inject
    TenantMatchmakerRefModelFactory TenantMatchmakerRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionMatchmakerRef_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, tenantProject);
        final var tenantStage = tenantStageModelFactory.create(tenant.getId(), tenantProject.getId());
        upsertStageOperation.upsertStage(shard, tenantStage);
        final var tenantVersion = tenantVersionModelFactory.create(tenant.getId(),
                tenantProject.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, tenantVersion);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                tenantStage.getId(),
                tenantVersion.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);

        final var VersionMatchmakerRef = TenantMatchmakerRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                matchmakerId());
        final var changeContext = upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                VersionMatchmakerRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenVersionMatchmakerRef_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, tenantProject);
        final var tenantStage = tenantStageModelFactory.create(tenant.getId(), tenantProject.getId());
        upsertStageOperation.upsertStage(shard, tenantStage);
        final var tenantVersion = tenantVersionModelFactory.create(tenant.getId(),
                tenantProject.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, tenantVersion);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                tenantStage.getId(),
                tenantVersion.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);
        final var VersionMatchmakerRef = TenantMatchmakerRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                matchmakerId());
        upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard, VersionMatchmakerRef);

        final var changeContext = upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                VersionMatchmakerRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var VersionMatchmakerRef = TenantMatchmakerRefModelFactory.create(tenantId(),
                versionId(),
                matchmakerId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                        VersionMatchmakerRef));
    }

    @Test
    void givenVersionMatchmakerRef_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, tenantProject);
        final var tenantStage = tenantStageModelFactory.create(tenant.getId(), tenantProject.getId());
        upsertStageOperation.upsertStage(shard, tenantStage);
        final var tenantVersion = tenantVersionModelFactory.create(tenant.getId(),
                tenantProject.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, tenantVersion);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                tenantStage.getId(),
                tenantVersion.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);
        final var VersionMatchmakerRef1 = TenantMatchmakerRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                matchmakerId());
        upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard, VersionMatchmakerRef1);

        final var VersionMatchmakerRef2 = TenantMatchmakerRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                matchmakerId(),
                VersionMatchmakerRef1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                        VersionMatchmakerRef2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }
}