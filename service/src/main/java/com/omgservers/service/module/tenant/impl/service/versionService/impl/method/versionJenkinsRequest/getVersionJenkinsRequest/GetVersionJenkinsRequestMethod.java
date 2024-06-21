package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.getVersionJenkinsRequest;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionJenkinsRequestMethod {
    Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(GetVersionJenkinsRequestRequest request);
}
