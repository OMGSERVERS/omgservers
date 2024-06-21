package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deleteVersion;

import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionDeveloperResponse> deleteVersion(DeleteVersionDeveloperRequest request);
}
