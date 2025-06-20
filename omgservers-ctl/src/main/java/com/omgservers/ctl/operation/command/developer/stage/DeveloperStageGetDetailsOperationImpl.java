package com.omgservers.ctl.operation.command.developer.stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.operation.client.CreateDeveloperClientOperation;
import com.omgservers.ctl.operation.ctl.OutputObjectOperation;
import com.omgservers.ctl.operation.wal.GetWalOperation;
import com.omgservers.ctl.operation.wal.developer.FindDeveloperTokenOperation;
import com.omgservers.ctl.operation.wal.installation.FindInstallationDetailsOperation;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperResponse;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperStageGetDetailsOperationImpl implements DeveloperStageGetDetailsOperation {

    final CreateDeveloperClientOperation createDeveloperClientOperation;
    final FindDeveloperTokenOperation findDeveloperTokenOperation;
    final FindInstallationDetailsOperation findInstallationDetailsOperation;
    final OutputObjectOperation outputObjectOperation;
    final GetWalOperation getWalOperation;

    final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void execute(final String tenant,
                        final String project,
                        final String stage,
                        final String service) {
        final var wal = getWalOperation.execute();

        final var installationDetails = findInstallationDetailsOperation.execute(wal, service);
        final var installationName = installationDetails.getName();
        final var installationUri = installationDetails.getAddress();

        final var developerTokenLog = findDeveloperTokenOperation.execute(wal, installationName);
        final var developerToken = developerTokenLog.getToken();
        final var developerClient = createDeveloperClientOperation.execute(installationUri, developerToken);

        final var request = new GetStageDetailsDeveloperRequest(tenant, project, stage);
        final var tenantDetails = developerClient.execute(request)
                .map(GetStageDetailsDeveloperResponse::getDetails)
                .await().indefinitely();

        outputObjectOperation.execute(tenantDetails);
    }
}
