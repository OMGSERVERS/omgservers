package com.omgservers.ctl.operation.command.developer.deployment;

import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.wal.AppendResultMapOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperDeploymentDeleteDeploymentOperationImpl implements DeveloperDeploymentDeleteDeploymentOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final AppendResultMapOperation appendResultMapOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final GetWalOperation getWalOperation;

    @Override
    public void execute(final Long deploymentId, final String installation) {
        final var wal = getWalOperation.execute();
        final var path = wal.getPath();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetails.getName();
        final var installationUri = installationDetails.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, installationName);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(installationUri, developerToken);

        final var request = new DeleteDeploymentDeveloperRequest(deploymentId);
        final var deleted = developerClient.execute(request)
                .map(DeleteDeploymentDeveloperResponse::getDeleted)
                .await().indefinitely();

        appendResultMapOperation.execute(path, KeyEnum.RESULT, deleted.toString());
    }
}
