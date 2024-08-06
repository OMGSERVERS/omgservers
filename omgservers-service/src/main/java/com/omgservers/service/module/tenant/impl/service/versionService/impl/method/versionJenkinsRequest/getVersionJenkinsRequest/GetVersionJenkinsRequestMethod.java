package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.getVersionJenkinsRequest;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionJenkinsRequestMethod {
    Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(GetVersionJenkinsRequestRequest request);
}
