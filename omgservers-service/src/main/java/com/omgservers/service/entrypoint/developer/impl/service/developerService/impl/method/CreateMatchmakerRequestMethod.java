package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface CreateMatchmakerRequestMethod {
    Uni<CreateMatchmakerRequestDeveloperResponse> execute(CreateMatchmakerRequestDeveloperRequest request);
}
