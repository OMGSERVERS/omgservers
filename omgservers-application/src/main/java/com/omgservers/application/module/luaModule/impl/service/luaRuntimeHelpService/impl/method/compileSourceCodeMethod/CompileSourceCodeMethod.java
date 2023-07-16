package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.compileSourceCodeMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CompileSourceCodeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CompileSourceCodeHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CompileSourceCodeMethod {
    Uni<CompileSourceCodeHelpResponse> compile(CompileSourceCodeHelpRequest request);
}
