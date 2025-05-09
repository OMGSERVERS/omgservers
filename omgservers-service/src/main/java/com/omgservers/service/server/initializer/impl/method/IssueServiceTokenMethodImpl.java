package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IssueServiceTokenMethodImpl implements IssueServiceTokenMethod {

    final StateService stateService;

    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public void execute() {
        log.debug("Issue service token");

        final var serviceToken = issueJwtTokenOperation.issueServiceJwtToken();
        setServiceToken(serviceToken);

        log.info("Service token issued");

    }

    void setServiceToken(final String serviceToken) {
        final var request = new SetServiceTokenRequest(serviceToken);
        stateService.execute(request);
    }
}
