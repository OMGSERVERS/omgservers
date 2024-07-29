package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.interchangeMethod;

import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerRequest;
import com.omgservers.schema.entrypoint.worker.InterchangeWorkerResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMethodImpl implements InterchangeMethod {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final JsonWebToken jwt;

    @Override
    public Uni<InterchangeWorkerResponse> interchange(final InterchangeWorkerRequest request) {
        final var userId = Long.valueOf(jwt.getClaim(Claims.sub));

        final var runtimeId = request.getRuntimeId();
        final var outgoingCommands = request.getOutgoingCommands();
        final var consumedCommands = request.getConsumedCommands();

        final var interchangeRequest = new InterchangeRequest(userId, runtimeId, outgoingCommands, consumedCommands);
        return runtimeModule.getRuntimeService().interchange(interchangeRequest)
                .map(InterchangeResponse::getIncomingCommands)
                .map(InterchangeWorkerResponse::new);
    }
}
