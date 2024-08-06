package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.deleteVersionJenkinsRequest;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionJenkinsRequestMethod {
    Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(DeleteVersionJenkinsRequestRequest request);
}
