package com.omgservers.module.lua.impl.service.luaService.impl;

import com.omgservers.dto.lua.CompileSourceCodeRequest;
import com.omgservers.dto.lua.CompileSourceCodeResponse;
import com.omgservers.module.lua.impl.service.luaService.LuaService;
import com.omgservers.module.lua.impl.service.luaService.impl.method.compileSourceCode.CompileSourceCodeMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class LuaServiceImpl implements LuaService {

    final CompileSourceCodeMethod compileSourceCodeHelpMethod;

    @Override
    public Uni<CompileSourceCodeResponse> compileSourceCode(final CompileSourceCodeRequest request) {
        return compileSourceCodeHelpMethod.compile(request);
    }
}
