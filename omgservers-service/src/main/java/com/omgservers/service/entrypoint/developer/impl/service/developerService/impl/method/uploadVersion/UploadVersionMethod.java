package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion;

import com.omgservers.model.dto.developer.UploadVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface UploadVersionMethod {
    Uni<UploadVersionDeveloperResponse> uploadVersion(UploadVersionDeveloperRequest request);
}
