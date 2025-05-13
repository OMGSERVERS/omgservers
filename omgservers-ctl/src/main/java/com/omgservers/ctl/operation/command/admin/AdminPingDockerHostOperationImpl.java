package com.omgservers.ctl.operation.command.admin;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateAdminClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.admin.AppendAdminTokenOperation;
import com.omgservers.ctl.operation.wal.admin.FindAdminTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminPingDockerHostOperationImpl implements AdminPingDockerHostOperation {

    final CreateAdminClientOperation createAdminClientOperation;
    final AppendAdminTokenOperation appendAdminTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final FindAdminTokenOperation findAdminTokenOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final URI dockerDaemonUri,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetailsLog = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetailsLog.getName();
        final var installationApi = installationDetailsLog.getApi();

        final var adminTokenLog = findAdminTokenOperation.execute(wal, installationName);
        final var adminToken = adminTokenLog.getToken();
        final var adminClient = createAdminClientOperation.execute(installationApi, adminToken);

        final var request = new PingDockerHostAdminRequest(dockerDaemonUri);
        final var result = adminClient.execute(request)
                .map(PingDockerHostAdminResponse::getSuccessful)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, String.valueOf(result));
    }
}
