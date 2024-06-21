package com.omgservers.service.entrypoint.server.impl.service.webService;

import com.omgservers.model.dto.server.BcryptHashServerRequest;
import com.omgservers.model.dto.server.BcryptHashServerResponse;
import com.omgservers.model.dto.server.GenerateIdServerResponse;
import com.omgservers.model.dto.server.PingServerServerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<PingServerServerResponse> pingServer();

    Uni<GenerateIdServerResponse> generateId();

    Uni<BcryptHashServerResponse> bcryptHash(BcryptHashServerRequest request);
}
