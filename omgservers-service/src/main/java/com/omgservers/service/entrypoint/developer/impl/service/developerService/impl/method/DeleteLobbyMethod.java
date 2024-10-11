package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteLobbyMethod {
    Uni<DeleteLobbyDeveloperResponse> execute(DeleteLobbyDeveloperRequest request);
}
