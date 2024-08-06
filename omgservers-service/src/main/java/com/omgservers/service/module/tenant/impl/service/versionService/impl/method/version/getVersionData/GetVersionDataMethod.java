package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersionData;

import com.omgservers.schema.module.tenant.version.GetVersionDataRequest;
import com.omgservers.schema.module.tenant.version.GetVersionDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionDataMethod {
    Uni<GetVersionDataResponse> getVersionData(GetVersionDataRequest request);
}
