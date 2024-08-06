package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.getVersion;

import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionResponse> getVersion(GetVersionRequest request);
}
