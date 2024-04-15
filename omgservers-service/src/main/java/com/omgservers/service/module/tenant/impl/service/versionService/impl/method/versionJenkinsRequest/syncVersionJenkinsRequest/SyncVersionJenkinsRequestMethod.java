package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.syncVersionJenkinsRequest;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionJenkinsRequestMethod {
    Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(SyncVersionJenkinsRequestRequest request);
}
