package com.omgservers.ctl.operation.command.admin;

import com.omgservers.ctl.operation.client.CreateAdminAnonymousClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.admin.AppendAdminTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminCreateTokenOperationImpl implements AdminCreateTokenOperation {

    final CreateAdminAnonymousClientOperation createAdminAnonymousClientOperation;
    final AppendAdminTokenOperation appendAdminTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String user,
                        final String password,
                        final String service) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrl = findInstallationDetailsOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getAddress();

        final var adminAnonymousClient = createAdminAnonymousClientOperation.execute(serviceUri);

        final var request = new CreateTokenAdminRequest(user, password);
        final var adminToken = adminAnonymousClient.execute(request)
                .map(CreateTokenAdminResponse::getRawToken)
                .await().indefinitely();

        appendAdminTokenOperation.execute(path, serviceName, user, adminToken);
    }
}
