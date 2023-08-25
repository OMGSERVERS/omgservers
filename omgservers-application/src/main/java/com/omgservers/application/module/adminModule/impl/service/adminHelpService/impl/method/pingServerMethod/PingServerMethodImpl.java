package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod;

import com.omgservers.dto.adminModule.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PingServerMethodImpl implements PingServerMethod {

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return Uni.createFrom().item(new PingServerAdminResponse("PONG"));
    }
}
