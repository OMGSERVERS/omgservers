package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.deleteVersion;

import com.omgservers.schema.module.tenant.DeleteVersionRequest;
import com.omgservers.schema.module.tenant.DeleteVersionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);
}
