package com.omgservers.ctl.operation.command.developer.stage;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperStageDeleteStageOperationImpl implements DeveloperStageDeleteStageOperation {

    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final String tenant,
                        final String project,
                        final String stage,
                        final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetails.getName();
        final var installationUri = installationDetails.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, installationName);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(installationUri, developerToken);

        final var request = new DeleteStageDeveloperRequest(tenant, project, stage);
        final var deleted = developerClient.execute(request)
                .map(DeleteStageDeveloperResponse::getDeleted)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, deleted.toString());
    }
}
