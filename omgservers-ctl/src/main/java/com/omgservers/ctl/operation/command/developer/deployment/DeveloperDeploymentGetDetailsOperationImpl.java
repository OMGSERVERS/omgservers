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
    public void execute(final Long deploymentId,
                        final String service,
                        final String user,
                        final boolean prettyPrint) {
        final var wal = getWalOperation.execute();

        final var serviceUrl = findInstallationDetailsOperation.execute(wal, service);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, serviceName, user);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        final var request = new GetDeploymentDetailsDeveloperRequest(deploymentId);
        final var deploymentDetails = developerClient.execute(request)
                .map(GetDeploymentDetailsDeveloperResponse::getDetails)
                .await().indefinitely();

        outputObjectOperation.execute(deploymentDetails, prettyPrint);
    }
}
