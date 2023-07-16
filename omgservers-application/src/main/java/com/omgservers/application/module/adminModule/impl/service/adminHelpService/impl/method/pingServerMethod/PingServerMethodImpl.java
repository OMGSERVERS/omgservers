package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
class PingServerMethodImpl implements PingServerMethod {

    @Override
    public Uni<PingServerHelpResponse> pingServer() {
        return Uni.createFrom().item(new PingServerHelpResponse("PONG"));
    }
}
