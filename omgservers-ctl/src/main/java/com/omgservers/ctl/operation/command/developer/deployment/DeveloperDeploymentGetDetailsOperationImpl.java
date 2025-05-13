package com.omgservers.ctl.operation.command.developer.deployment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.ctl.OutputObjectOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperDeploymentGetDetailsOperationImpl implements DeveloperDeploymentGetDetailsOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final OutputObjectOperation outputObjectOperation;
    final GetWalOperation getWalOperation;

    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void execute(final Long deploymentId, final String installation) {
        final var wal = getWalOperation.execute();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, installation);
        final var installationName = installationDetails.getName();
        final var installationUri = installationDetails.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, installationName);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(installationUri, developerToken);

        final var request = new GetDeploymentDetailsDeveloperRequest(deploymentId);
        final var deploymentDetails = developerClient.execute(request)
                .map(GetDeploymentDetailsDeveloperResponse::getDetails)
                .await().indefinitely();

        outputObjectOperation.execute(deploymentDetails);
    }
}
