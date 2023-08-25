package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod;

import com.omgservers.dto.adminModule.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;

public interface PingServerMethod {
    Uni<PingServerAdminResponse> pingServer();
}
