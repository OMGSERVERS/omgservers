package com.omgservers.ctl.operation.command.developer.version;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperVersionDeleteVersionOperationImpl implements DeveloperVersionDeleteVersionOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String version,
                        final String service,
                        final String user) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var serviceUrl = findInstallationDetailsOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, serviceName, user);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        final var request = new DeleteVersionDeveloperRequest(tenant, Long.valueOf(version));
        final var deleted = developerClient.execute(request)
                .map(DeleteVersionDeveloperResponse::getDeleted)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, deleted.toString());
    }
}
