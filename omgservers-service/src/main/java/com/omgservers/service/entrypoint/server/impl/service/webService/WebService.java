package com.omgservers.service.entrypoint.server.impl.service.webService;

import com.omgservers.schema.entrypoint.server.BcryptHashServerRequest;
import com.omgservers.schema.entrypoint.server.BcryptHashServerResponse;
import com.omgservers.schema.entrypoint.server.GenerateIdServerResponse;
import com.omgservers.schema.entrypoint.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<PingServerServerResponse> pingServer();

    Uni<GenerateIdServerResponse> generateId();

    Uni<BcryptHashServerResponse> bcryptHash(BcryptHashServerRequest request);
}
