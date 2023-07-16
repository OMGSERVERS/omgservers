package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createPlayerContextMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreatePlayerContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreatePlayerContextHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreatePlayerContextMethod {

    Uni<CreatePlayerContextHelpResponse> createPlayerContext(CreatePlayerContextHelpRequest request);
}
