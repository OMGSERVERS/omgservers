package com.omgservers.connector.server.initializer.impl.method;

import com.omgservers.connector.operation.CreateServiceAnonymousClientOperation;
import com.omgservers.connector.operation.ExecuteStateOperation;
import com.omgservers.connector.operation.GetConnectorConfigOperation;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateConnectorTokenMethodImpl implements CreateConnectorTokenMethod {

    final CreateServiceAnonymousClientOperation createServiceAnonymousClientOperation;
    final GetConnectorConfigOperation getConnectorConfigOperation;
    final ExecuteStateOperation executeStateOperation;

    @Override
    public void execute() {
        log.info("Create connector token");

        final var userAlias = getConnectorConfigOperation.getConnectorConfig().user().alias();
        final var userPassword = getConnectorConfigOperation.getConnectorConfig().user().password();

        final var serviceUri = getConnectorConfigOperation.getConnectorConfig().serviceUri();
        final var serviceClient = createServiceAnonymousClientOperation.execute(serviceUri);
        final var createTokenConnectorRequest = new CreateTokenConnectorRequest(userAlias, userPassword);

        final var connectorToken = Uni.createFrom().voidItem()
                .flatMap(voidItem -> serviceClient.execute(createTokenConnectorRequest))
                .map(CreateTokenConnectorResponse::getRawToken)
                .onFailure()
                .recoverWithUni(t -> {
                    log.info("Failed, trying again, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().failure(t);
                })
                .onFailure()
                .retry().withBackOff(Duration.ofSeconds(1)).indefinitely()
                .await().indefinitely();

        executeStateOperation.setConnectorToken(connectorToken);

        log.info("Connector token created");
    }
}
