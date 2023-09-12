package com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersion;

import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);
}