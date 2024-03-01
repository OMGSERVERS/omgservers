package com.omgservers.service.module.worker.impl.service.workerService.impl.method.interchangeMethod;

import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMethodImpl implements InterchangeMethod {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<InterchangeWorkerResponse> interchange(final InterchangeWorkerRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var runtimeId = request.getRuntimeId();
        final var outgoingCommands = request.getOutgoingCommands();
        final var consumedCommands = request.getConsumedCommands();

        final var interchangeRequest = new InterchangeRequest(userId, runtimeId, outgoingCommands, consumedCommands);
        return runtimeModule.getRuntimeService().interchange(interchangeRequest)
                .map(InterchangeResponse::getIncomingCommands)
                .map(InterchangeWorkerResponse::new);
    }
}
