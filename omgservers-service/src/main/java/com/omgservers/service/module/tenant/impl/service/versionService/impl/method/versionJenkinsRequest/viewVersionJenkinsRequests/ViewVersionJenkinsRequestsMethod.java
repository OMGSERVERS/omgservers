package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.viewVersionJenkinsRequests;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionJenkinsRequestsMethod {
    Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(ViewVersionJenkinsRequestsRequest request);
}
