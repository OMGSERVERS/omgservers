package com.omgservers.module.lua.impl.service.luaService;

import com.omgservers.model.dto.lua.CompileSourceCodeRequest;
import com.omgservers.model.dto.lua.CompileSourceCodeResponse;
import io.smallrye.mutiny.Uni;

public interface LuaService {

    Uni<CompileSourceCodeResponse> compileSourceCode(CompileSourceCodeRequest request);
}
