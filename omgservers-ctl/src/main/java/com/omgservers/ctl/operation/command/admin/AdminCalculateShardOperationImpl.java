package com.omgservers.ctl.operation.command.admin;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateAdminClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.admin.AppendAdminTokenOperation;
import com.omgservers.ctl.operation.wal.admin.FindAdminTokenOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
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
    final FindServiceUrlOperation findServiceUrlOperation;
    final FindAdminTokenOperation findAdminTokenOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String key,
                        final String service,
                        final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrlLog = findServiceUrlOperation.execute(wal, service);
        final var serviceName = serviceUrlLog.getName();
        final var serviceUri = serviceUrlLog.getUri();

        final var adminTokenLog = findAdminTokenOperation.execute(wal, serviceName, user);
        final var adminToken = adminTokenLog.getToken();
        final var adminClient = createAdminClientOperation.execute(serviceUri, adminToken);

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
