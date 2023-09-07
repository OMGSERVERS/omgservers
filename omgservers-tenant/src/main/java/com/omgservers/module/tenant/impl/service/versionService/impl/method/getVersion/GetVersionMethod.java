package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersion;

import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionResponse> getVersion(GetVersionRequest request);
}
