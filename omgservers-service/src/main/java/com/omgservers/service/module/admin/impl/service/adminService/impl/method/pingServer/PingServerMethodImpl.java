package com.omgservers.service.module.admin.impl.service.adminService.impl.method.pingServer;

import com.omgservers.model.dto.admin.PingServerAdminResponse;
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
