package com.omgservers.module.lua.impl.service.luaService.impl.method.compileSourceCode;

import com.omgservers.dto.lua.CompileSourceCodeRequest;
import com.omgservers.dto.lua.CompileSourceCodeResponse;
import io.smallrye.mutiny.Uni;

public interface CompileSourceCodeMethod {
    Uni<CompileSourceCodeResponse> compile(CompileSourceCodeRequest request);
}
