package com.omgservers.service.entrypoint.server.impl.service.serverService;

import com.omgservers.model.dto.server.BcryptHashServerRequest;
import com.omgservers.model.dto.server.BcryptHashServerResponse;
import com.omgservers.model.dto.server.GenerateIdServerResponse;
import com.omgservers.model.dto.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServerService {
    Uni<PingServerServerResponse> pingServer();

    Uni<GenerateIdServerResponse> generateId();

    Uni<BcryptHashServerResponse> bcryptHash(@Valid BcryptHashServerRequest request);
}
