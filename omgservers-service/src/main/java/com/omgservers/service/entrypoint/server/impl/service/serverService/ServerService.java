package com.omgservers.service.entrypoint.server.impl.service.serverService;

import com.omgservers.schema.entrypoint.server.BcryptHashServerRequest;
import com.omgservers.schema.entrypoint.server.BcryptHashServerResponse;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServerService {
    Uni<PingServerServerResponse> pingServer();

    Uni<GenerateIdServerResponse> generateId();

    Uni<BcryptHashServerResponse> bcryptHash(@Valid BcryptHashServerRequest request);
}
