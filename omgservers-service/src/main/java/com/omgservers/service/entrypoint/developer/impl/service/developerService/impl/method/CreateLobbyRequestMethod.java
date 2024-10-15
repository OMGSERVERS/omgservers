package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateLobbyRequestMethod {
    Uni<CreateLobbyRequestDeveloperResponse> execute(CreateLobbyRequestDeveloperRequest request);
}
