package com.omgservers.service.entrypoint.server.impl.service.serverService;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServerService {
    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<BcryptHashAdminResponse> bcryptHash(@Valid BcryptHashAdminRequest request);
}
