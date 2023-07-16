package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createMatchContextMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateMatchContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateMatchContextHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateMatchContextMethod {

    Uni<CreateMatchContextHelpResponse> createMatchContext(CreateMatchContextHelpRequest request);
}
