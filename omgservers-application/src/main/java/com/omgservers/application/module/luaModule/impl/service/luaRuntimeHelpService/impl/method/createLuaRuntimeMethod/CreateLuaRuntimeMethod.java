package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createLuaRuntimeMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CreateLuaRuntimeMethod {

    Uni<CreateLuaRuntimeHelpResponse> createLuaRuntime(CreateLuaRuntimeHelpRequest request);
}
