package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.pingServerMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface PingServerMethod {
    Uni<PingServerHelpResponse> pingServer();
}
