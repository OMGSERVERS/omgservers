package com.omgservers.connector.server.initializer.impl.method;

import com.omgservers.connector.operation.CreateConnectorTokenOperation;
import com.omgservers.connector.operation.ExecuteStateOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateConnectorTokenMethodImpl implements CreateConnectorTokenMethod {

    final CreateConnectorTokenOperation createConnectorTokenOperation;
    final ExecuteStateOperation executeStateOperation;

    @Override
    public void execute() {
        log.info("Create connector token");

        final var connectorToken = Uni.createFrom().voidItem()
                .flatMap(voidItem -> createConnectorTokenOperation.execute())
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
