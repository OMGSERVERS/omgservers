package com.omgservers.ctl.operation.command.admin;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateAdminClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.admin.AppendAdminTokenOperation;
import com.omgservers.ctl.operation.wal.admin.FindAdminTokenOperation;
import com.omgservers.ctl.operation.wal.service.FindServiceUrlOperation;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminBcryptHashOperationImpl implements AdminBcryptHashOperation {

    final CreateAdminClientOperation createAdminClientOperation;
    final AppendAdminTokenOperation appendAdminTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindServiceUrlOperation findServiceUrlOperation;
    final FindAdminTokenOperation findAdminTokenOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String value,
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

        final var request = new BcryptHashAdminRequest(value);
        final var hash = adminClient.execute(request)
                .map(BcryptHashAdminResponse::getHash)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.HASH, hash);
    }
}
