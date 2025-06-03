package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.operation.server.ExecuteStateOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueServiceTokenMethodImpl implements IssueServiceTokenMethod {

    final IssueJwtTokenOperation issueJwtTokenOperation;

    final ExecuteStateOperation executeStateOperation;

    @Override
    public void execute() {
        log.debug("Issue service token");

        final var serviceToken = issueJwtTokenOperation.issueServiceJwtToken();
        executeStateOperation.setServiceToken(serviceToken);

        log.info("Service token issued");

    }
}
