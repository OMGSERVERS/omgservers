package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.bootstrapIndex;

import com.omgservers.model.dto.server.BootstrapIndexServerRequest;
import com.omgservers.model.dto.server.BootstrapIndexServerResponse;
import io.smallrye.mutiny.Uni;

public interface BootstrapIndexMethod {
    Uni<BootstrapIndexServerResponse> bootstrapIndex(BootstrapIndexServerRequest request);
}
