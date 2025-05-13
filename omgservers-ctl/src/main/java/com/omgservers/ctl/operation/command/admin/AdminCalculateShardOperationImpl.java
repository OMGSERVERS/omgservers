package com.omgservers.ctl.operation.command.admin;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateAdminClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.admin.AppendAdminTokenOperation;
import com.omgservers.ctl.operation.wal.admin.FindAdminTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminCalculateShardOperationImpl implements AdminCalculateShardOperation {

    final CreateAdminClientOperation createAdminClientOperation;
    final AppendAdminTokenOperation appendAdminTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final FindAdminTokenOperation findAdminTokenOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String key,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetailsLog = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetailsLog.getName();
        final var installationApi = installationDetailsLog.getApi();

        final var adminTokenLog = findAdminTokenOperation.execute(wal, installationName);
        final var adminToken = adminTokenLog.getToken();
        final var adminClient = createAdminClientOperation.execute(installationApi, adminToken);

        final var request = new CalculateShardAdminRequest(key);
        final var calculateShardAdminResponse = adminClient.execute(request)
                .await().indefinitely();

        final var uri = calculateShardAdminResponse.getUri();
        final var slot = calculateShardAdminResponse.getSlot();

        appendResultMapOperation.execute(path, Map.of(
                KeyEnum.URI, uri.toString(),
                KeyEnum.SLOT, String.valueOf(slot)));
    }
}
