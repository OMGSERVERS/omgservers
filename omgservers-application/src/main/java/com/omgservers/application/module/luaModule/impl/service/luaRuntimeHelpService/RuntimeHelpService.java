package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CompileSourceCodeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateMatchContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreatePlayerContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CompileSourceCodeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateMatchContextHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreatePlayerContextHelpResponse;
import io.smallrye.mutiny.Uni;

public interface RuntimeHelpService {
    Uni<CompileSourceCodeHelpResponse> compileSourceCode(CompileSourceCodeHelpRequest request);

    Uni<CreateLuaRuntimeHelpResponse> createLuaRuntime(CreateLuaRuntimeHelpRequest request);

    Uni<CreatePlayerContextHelpResponse> createPlayerContext(CreatePlayerContextHelpRequest request);

    Uni<CreateMatchContextHelpResponse> createMatchContext(CreateMatchContextHelpRequest request);
}
