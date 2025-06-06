package com.omgservers.ctl.operation.command.support.installation;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.initializer.CreateInitializerOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportInstallationInitTenantOperationImpl implements SupportInstallationInitTenantOperation {

    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final CreateSupportClientOperation createSupportClientOperation;
    final CreateInitializerOperation createInitializerOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String developer,
                        final String tenant,
                        final String project,
                        final String stage,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetailsLog = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetailsLog.getName();
        final var installationApi = installationDetailsLog.getAddress();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, installationName);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(installationApi, supportToken);

        final var initializer = createInitializerOperation.execute(supportClient);
        initializer.createDeveloper();
        initializer.createDeveloperAlias(developer);
        initializer.createTenant();
        initializer.createTenantAlias(tenant);
        initializer.createTenantPermission();
        initializer.createProject();
        initializer.createProjectAlias(project);
        initializer.createProjectPermission();
        initializer.createStageAlias(stage);
        initializer.createStagePermission();

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.DEVELOPER, initializer.getDeveloperAlias(),
                KeyEnum.PASSWORD, initializer.getDeveloperPassword(),
                KeyEnum.TENANT, initializer.getTenantAlias(),
                KeyEnum.PROJECT, initializer.getProjectAlias(),
                KeyEnum.STAGE, initializer.getStageAlias()));
    }
}
