package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.deleteVersionJenkinsRequest;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionJenkinsRequestMethod {
    Uni<DeleteVersionJenkinsRequestResponse> deleteVersionJenkinsRequest(DeleteVersionJenkinsRequestRequest request);
}
