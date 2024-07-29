package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion;

import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface UploadVersionMethod {
    Uni<UploadVersionDeveloperResponse> uploadVersion(UploadVersionDeveloperRequest request);
}
