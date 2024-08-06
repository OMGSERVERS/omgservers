package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.viewVersionLobbyRefs;

import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionLobbyRefsMethod {
    Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(ViewVersionLobbyRefsRequest request);
}
