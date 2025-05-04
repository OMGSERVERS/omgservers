package com.omgservers.ctl.operation.command.support.project;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateSupportClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.ctl.operation.wal.support.FindSupportTokenOperation;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportProjectCreateProjectOperationImpl implements SupportProjectCreateProjectOperation {

    final CreateSupportClientOperation createSupportClientOperation;
    final FindSupportTokenOperation findSupportTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String service,
                        final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetailsLog = findInstallationDetailsOperation.execute(wal, service);
        final var installationName = installationDetailsLog.getName();
        final var installationApi = installationDetailsLog.getApi();

        final var supportTokenLog = findSupportTokenOperation.execute(wal, installationName, user);
        final var supportToken = supportTokenLog.getToken();
        final var supportClient = createSupportClientOperation.execute(installationApi, supportToken);

        final var request = new CreateTenantProjectSupportRequest(tenant);
        final var createTenantProjectSupportResponse = supportClient.execute(request)
                .await().indefinitely();

        final var projectId = createTenantProjectSupportResponse.getProjectId();
        final var stageId = createTenantProjectSupportResponse.getStageId();

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.PROJECT_ID, projectId.toString(),
                KeyEnum.STAGE_ID, stageId.toString()));
    }
}
