package com.omgservers.ctl.operation.command.localtesting;

import com.omgservers.ctl.client.SupportClient;
import com.omgservers.ctl.operation.client.CreateLocalTestingDeveloperClientOperation;
import com.omgservers.ctl.operation.client.CreateLocalTestingSupportClientOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.localtesting.AppendTestTenantOperation;
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
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LocalTestingInitTenantOperationImpl implements LocalTestingInitTenantOperation {

    final CreateLocalTestingDeveloperClientOperation createLocalTestingDeveloperClientOperation;
    final CreateLocalTestingSupportClientOperation createLocalTestingSupportClientOperation;
    final AppendTestTenantOperation appendTestTenantOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var supportClient = createLocalTestingSupportClientOperation.execute();

        final var initializer = new Initializer(supportClient);
        initializer.createDeveloper();
        initializer.createTenant();
        initializer.createTenantAlias(tenant);
        initializer.createTenantPermission();
        initializer.createProject();
        initializer.createProjectAlias(project);
        initializer.createProjectPermission();
        initializer.createStageAlias(stage);
        initializer.createStagePermission();

        appendTestTenantOperation.execute(path,
                initializer.developerUser,
                initializer.developerPassword,
                initializer.tenantAlias,
                initializer.projectAlias,
                initializer.stageAlias);
    }

    @RequiredArgsConstructor
    class Initializer {

        final SupportClient supportClient;

        String developerUser;
        String developerPassword;

        Long tenantId;
        String tenantAlias;

        Long projectId;
        String projectAlias;

        Long stageId;
        String stageAlias;

        void createDeveloper() {
            final var request = new CreateDeveloperSupportRequest();
            final var response = supportClient.execute(request)
                    .await().indefinitely();

            developerUser = response.getUserId().toString();
            developerPassword = response.getPassword();
            log.debug("Developer \"{}\" created", developerUser);
        }

        void createTenant() {
            final var request = new CreateTenantSupportRequest();
            tenantId = supportClient.execute(request)
                    .map(CreateTenantSupportResponse::getId)
                    .await().indefinitely();
            log.debug("Tenant \"{}\" created", tenantId);
        }

        void createTenantAlias(final String alias) {
            final var request = new CreateTenantAliasSupportRequest(tenantId, alias);
            supportClient.execute(request)
                    .await().indefinitely();
            tenantAlias = alias;
            log.debug("Tenant alias \"{}\" assigned", alias);
        }

        void createTenantPermission() {
            final var permissions = Set.of(TenantPermissionQualifierEnum.PROJECT_MANAGER,
                    TenantPermissionQualifierEnum.TENANT_VIEWER);
            final var request = new CreateTenantPermissionsSupportRequest(tenantAlias,
                    Long.valueOf(developerUser),
                    permissions);
            final var response = supportClient.execute(request)
                    .await().indefinitely();
            log.debug("Tenant permissions \"{}\" created", response.getCreatedPermissions());
        }

        void createProject() {
            final var request = new CreateTenantProjectSupportRequest(tenantAlias);
            final var response = supportClient.execute(request)
                    .await().indefinitely();
            projectId = response.getProjectId();
            stageId = response.getStageId();
            log.debug("Project \"{}\" created", projectId);
            log.debug("Stage \"{}\" created", stageId);
        }

        void createProjectAlias(final String alias) {
            final var request = new CreateTenantProjectAliasSupportRequest(tenantAlias, projectId, alias);
            supportClient.execute(request)
                    .await().indefinitely();
            projectAlias = alias;
            log.debug("Project alias \"{}\" assigned", alias);
        }

        void createProjectPermission() {
            final var permissions = Set.of(TenantProjectPermissionQualifierEnum.STAGE_MANAGER,
                    TenantProjectPermissionQualifierEnum.VERSION_MANAGER,
                    TenantProjectPermissionQualifierEnum.PROJECT_VIEWER);
            final var request = new CreateTenantProjectPermissionsSupportRequest(tenantAlias,
                    projectAlias,
                    Long.valueOf(developerUser),
                    permissions);
            final var response = supportClient.execute(request)
                    .await().indefinitely();
            log.debug("Project permissions \"{}\" created", response.getCreatedPermissions());
        }

        void createStageAlias(final String alias) {
            final var request = new CreateTenantStageAliasSupportRequest(tenantAlias, stageId, alias);
            supportClient.execute(request)
                    .await().indefinitely();
            stageAlias = alias;
            log.debug("Stage alias \"{}\" assigned", alias);
        }

        void createStagePermission() {
            final var permissions = Set.of(TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER,
                    TenantStagePermissionQualifierEnum.STAGE_VIEWER);
            final var request = new CreateTenantStagePermissionsSupportRequest(tenantAlias,
                    projectAlias,
                    stageAlias,
                    Long.valueOf(developerUser),
                    permissions);
            final var response = supportClient.execute(request)
                    .await().indefinitely();
            log.debug("Stage permissions \"{}\" created", response.getCreatedPermissions());
        }
    }
}
