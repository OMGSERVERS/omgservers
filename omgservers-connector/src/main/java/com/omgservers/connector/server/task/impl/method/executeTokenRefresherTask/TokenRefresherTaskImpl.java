package com.omgservers.connector.server.task.impl.method.executeTokenRefresherTask;

import com.omgservers.connector.operation.CreateConnectorTokenOperation;
import com.omgservers.connector.operation.ExecuteStateOperation;
import com.omgservers.connector.operation.Task;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenRefresherTaskImpl implements Task<TokenRefresherTaskArguments> {

    final CreateConnectorTokenOperation createConnectorTokenOperation;
    final ExecuteStateOperation executeStateOperation;

    @Override
    public Uni<Boolean> execute(final TokenRefresherTaskArguments taskArguments) {
        log.info("Create a new connector token");
        return createConnectorTokenOperation.execute()
                .invoke(executeStateOperation::setConnectorToken)
                .replaceWith(Boolean.FALSE);
    }
}
