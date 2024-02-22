package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionLobbyRefs;

import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionLobbyRefsMethod {
    Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(ViewVersionLobbyRefsRequest request);
}
