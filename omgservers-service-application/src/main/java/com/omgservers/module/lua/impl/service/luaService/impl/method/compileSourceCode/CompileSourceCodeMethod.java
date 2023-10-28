package com.omgservers.module.lua.impl.service.luaService.impl.method.compileSourceCode;

import com.omgservers.model.dto.lua.CompileSourceCodeRequest;
import com.omgservers.model.dto.lua.CompileSourceCodeResponse;
import io.smallrye.mutiny.Uni;

public interface CompileSourceCodeMethod {
    Uni<CompileSourceCodeResponse> compile(CompileSourceCodeRequest request);
}
