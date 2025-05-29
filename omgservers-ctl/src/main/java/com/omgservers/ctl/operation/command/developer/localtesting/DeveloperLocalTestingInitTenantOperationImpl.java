package com.omgservers.ctl.operation.command.developer.localtesting;

import com.omgservers.ctl.operation.client.CreateLocalSupportClientOperation;
import com.omgservers.ctl.operation.initializer.CreateInitializerOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.local.AppendLocalTenantOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperLocalTestingInitTenantOperationImpl implements DeveloperLocalTestingInitTenantOperation {

    final CreateLocalSupportClientOperation createLocalSupportClientOperation;
    final CreateInitializerOperation createInitializerOperation;
    final AppendLocalTenantOperation appendLocalTenantOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var supportClient = createLocalSupportClientOperation.execute();

        final var initializer = createInitializerOperation.execute(supportClient);
        initializer.createDeveloper();
        initializer.createTenant();
        initializer.createTenantAlias(tenant);
        initializer.createTenantPermission();
        initializer.createProject();
        initializer.createProjectAlias(project);
        initializer.createProjectPermission();
        initializer.createStageAlias(stage);
        initializer.createStagePermission();

        appendLocalTenantOperation.execute(path,
                initializer.getDeveloperUserId().toString(),
                initializer.getDeveloperPassword(),
                initializer.getTenantAlias(),
                initializer.getProjectAlias(),
                initializer.getStageAlias());
    }
}
