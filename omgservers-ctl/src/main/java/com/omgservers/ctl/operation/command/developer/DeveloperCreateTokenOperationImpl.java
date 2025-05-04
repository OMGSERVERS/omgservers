package com.omgservers.ctl.operation.command.developer;

import com.omgservers.ctl.operation.client.CreateDeveloperAnonymousClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.AppendDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperCreateTokenOperationImpl implements DeveloperCreateTokenOperation {

    final CreateDeveloperAnonymousClientOperation createDeveloperAnonymousClientOperation;
    final AppendDeveloperTokenOperation appendDeveloperTokenOperation;
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
        final var serviceUri = serviceUrl.getApi();

        final var developerAnonymousClient = createDeveloperAnonymousClientOperation.execute(serviceUri);

        final var request = new CreateTokenDeveloperRequest(Long.valueOf(user), password);
        final var adminToken = developerAnonymousClient.execute(request)
                .map(CreateTokenDeveloperResponse::getRawToken)
                .await().indefinitely();

        appendDeveloperTokenOperation.execute(path, serviceName, user, adminToken);
    }
}
