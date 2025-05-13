package com.omgservers.ctl.operation.command.developer.project;

import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.ctl.OutputObjectOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperProjectGetDetailsOperationImpl implements DeveloperProjectGetDetailsOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final OutputObjectOperation outputObjectOperation;
    final GetWalOperation getWalOperation;

    @Override
    @SneakyThrows
    public void execute(final String tenant,
                        final String project,
                        final String installation) {
        final var wal = getWalOperation.execute();

        final var serviceUrl = findInstallationDetailsOperation.execute(wal, installation);
        final var serviceName = serviceUrl.getName();
        final var serviceUri = serviceUrl.getApi();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, serviceName);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(serviceUri, developerToken);

        final var request = new GetProjectDetailsDeveloperRequest(tenant, project);
        final var tenantDetails = developerClient.execute(request)
                .map(GetProjectDetailsDeveloperResponse::getDetails)
                .await().indefinitely();

        outputObjectOperation.execute(tenantDetails);
    }
}
