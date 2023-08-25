package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.compileSourceCodeMethod.CompileSourceCodeMethod;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createLuaRuntimeMethod.CreateLuaRuntimeMethod;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createMatchContextMethod.CreateMatchContextMethod;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createPlayerContextMethod.CreatePlayerContextMethod;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CompileSourceCodeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateMatchContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreatePlayerContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CompileSourceCodeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateMatchContextHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreatePlayerContextHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class LuaHelpServiceImpl implements RuntimeHelpService {

    final CreatePlayerContextMethod createPlayerContextHelpMethod;
    final CompileSourceCodeMethod compileSourceCodeHelpMethod;
    final CreateLuaRuntimeMethod createLuaRuntimeHelpMethod;
    final CreateMatchContextMethod createMatchContextMethod;

    @Override
    public Uni<CompileSourceCodeHelpResponse> compileSourceCode(final CompileSourceCodeHelpRequest request) {
        return compileSourceCodeHelpMethod.compile(request);
    }

    @Override
    public Uni<CreateLuaRuntimeHelpResponse> createLuaRuntime(final CreateLuaRuntimeHelpRequest request) {
        return createLuaRuntimeHelpMethod.createLuaRuntime(request);
    }

    @Override
    public Uni<CreatePlayerContextHelpResponse> createPlayerContext(final CreatePlayerContextHelpRequest request) {
        return createPlayerContextHelpMethod.createPlayerContext(request);
    }

    @Override
    public Uni<CreateMatchContextHelpResponse> createMatchContext(final CreateMatchContextHelpRequest request) {
        return createMatchContextMethod.createMatchContext(request);
    }
}
