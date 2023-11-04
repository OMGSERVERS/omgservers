package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersion;

import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMethod {

    Uni<GetVersionResponse> getVersion(GetVersionRequest request);
}
