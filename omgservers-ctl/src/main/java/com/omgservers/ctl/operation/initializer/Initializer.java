package com.omgservers.ctl.operation.initializer;

import com.omgservers.ctl.client.SupportClient;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Getter
@RequiredArgsConstructor
public class Initializer {

    final SupportClient supportClient;

    Long developerUserId;
    String developerPassword;
    String developerAlias;

    Long tenantId;
    String tenantAlias;

    Long projectId;
    String projectAlias;

    Long stageId;
    String stageAlias;

    public void createDeveloper() {
        final var request = new CreateDeveloperSupportRequest();
        final var response = supportClient.execute(request)
                .await().indefinitely();

        developerUserId = response.getUserId();
        developerPassword = response.getPassword();
        log.info("Developer \"{}\" created", developerUserId);
    }

    public void createDeveloperAlias(final String alias) {
        final var request = new CreateDeveloperAliasSupportRequest(developerUserId, alias);
        supportClient.execute(request).await().indefinitely();
        developerAlias = alias;
        log.info("Developer alias \"{}\" assigned", alias);
    }

    public void createTenant() {
        final var request = new CreateTenantSupportRequest();
        tenantId = supportClient.execute(request)
                .map(CreateTenantSupportResponse::getId)
                .await().indefinitely();
        log.info("Tenant \"{}\" created", tenantId);
    }

    public void createTenantAlias(final String alias) {
        final var request = new CreateTenantAliasSupportRequest(tenantId, alias);
        supportClient.execute(request)
                .await().indefinitely();
        tenantAlias = alias;
        log.info("Tenant alias \"{}\" assigned", alias);
    }

    public void createTenantPermission() {
        final var permissions = Set.of(TenantPermissionQualifierEnum.PROJECT_MANAGER,
                TenantPermissionQualifierEnum.TENANT_VIEWER);
        final var request = new CreateTenantPermissionsSupportRequest(tenantAlias,
                developerAlias,
                permissions);
        final var response = supportClient.execute(request)
                .await().indefinitely();
        log.info("Tenant permissions \"{}\" created", response.getCreatedPermissions());
    }

    public void createProject() {
        final var request = new CreateTenantProjectSupportRequest(tenantAlias);
        final var response = supportClient.execute(request)
                .await().indefinitely();
        projectId = response.getProjectId();
        stageId = response.getStageId();
        log.info("Project \"{}\" created", projectId);
        log.info("Stage \"{}\" created", stageId);
    }

    public void createProjectAlias(final String alias) {
        final var request = new CreateTenantProjectAliasSupportRequest(tenantAlias, projectId, alias);
        supportClient.execute(request)
                .await().indefinitely();
        projectAlias = alias;
        log.info("Project alias \"{}\" assigned", alias);
    }

    public void createProjectPermission() {
        final var permissions = Set.of(TenantProjectPermissionQualifierEnum.STAGE_MANAGER,
                TenantProjectPermissionQualifierEnum.VERSION_MANAGER,
                TenantProjectPermissionQualifierEnum.PROJECT_VIEWER);
        final var request = new CreateTenantProjectPermissionsSupportRequest(tenantAlias,
                projectAlias,
                developerAlias,
                permissions);
        final var response = supportClient.execute(request)
                .await().indefinitely();
        log.info("Project permissions \"{}\" created", response.getCreatedPermissions());
    }

    public void createStageAlias(final String alias) {
        final var request = new CreateTenantStageAliasSupportRequest(tenantAlias, stageId, alias);
        supportClient.execute(request)
                .await().indefinitely();
        stageAlias = alias;
        log.info("Stage alias \"{}\" assigned", alias);
    }

    public void createStagePermission() {
        final var permissions = Set.of(TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER,
                TenantStagePermissionQualifierEnum.STAGE_VIEWER);
        final var request = new CreateTenantStagePermissionsSupportRequest(tenantAlias,
                projectAlias,
                stageAlias,
                developerAlias,
                permissions);
        final var response = supportClient.execute(request)
                .await().indefinitely();
        log.info("Stage permissions \"{}\" created", response.getCreatedPermissions());
    }
}
