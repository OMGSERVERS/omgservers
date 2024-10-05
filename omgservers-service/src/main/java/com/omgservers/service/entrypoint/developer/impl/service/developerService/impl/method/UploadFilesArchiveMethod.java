package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface UploadFilesArchiveMethod {
    Uni<UploadFilesArchiveDeveloperResponse> execute(UploadFilesArchiveDeveloperRequest request);
}
