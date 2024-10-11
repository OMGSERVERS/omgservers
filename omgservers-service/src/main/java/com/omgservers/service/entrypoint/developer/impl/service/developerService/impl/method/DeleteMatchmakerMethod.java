package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchmakerMethod {
    Uni<DeleteMatchmakerDeveloperResponse> execute(DeleteMatchmakerDeveloperRequest request);
}
