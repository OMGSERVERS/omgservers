package com.omgservers.module.lua.impl.service.luaService;

import com.omgservers.dto.lua.CompileSourceCodeRequest;
import com.omgservers.dto.lua.CompileSourceCodeResponse;
import io.smallrye.mutiny.Uni;

public interface LuaService {

    Uni<CompileSourceCodeResponse> compileSourceCode(CompileSourceCodeRequest request);
}
