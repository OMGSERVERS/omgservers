package com.omgservers.ctl.operation.command.support.stage;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.dto.permission.StagePermissionEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportStageCreatePermissionOperationImpl implements SupportStageCreatePermissionOperation {

    final CreateSupportClientOperation createSupportClientOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage,
                        final String developer,
                        final StagePermissionEnum permission,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetailsLog = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetailsLog.getName();
        final var installationApi = installationDetailsLog.getApi();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, installationName);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(installationApi, supportToken);

        final var qualifier = permission.toQualifier();
        final var request = new CreateTenantStagePermissionsSupportRequest(tenant,
                project,
                stage,
                developer,
                Collections.singleton(qualifier));
        final var result = supportClient.execute(request)
                .map(CreateTenantStagePermissionsSupportResponse::getCreatedPermissions)
                .map(List::size)
                .map(size -> size > 0)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, result.toString());
    }
}
