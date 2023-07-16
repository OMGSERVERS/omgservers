package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.compileSourceCodeMethod;

import com.omgservers.application.module.luaModule.impl.operation.compileSourceCodeOperation.CompileSourceCodeOperation;
import com.omgservers.application.module.luaModule.impl.operation.createServerGlobalsOperation.CreateServerGlobalsOperation;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CompileSourceCodeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CompileSourceCodeHelpResponse;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class CompileSourceCodeMethodImpl implements CompileSourceCodeMethod {

    final CreateServerGlobalsOperation createServerGlobalsOperation;
    final CompileSourceCodeOperation compileSourceCodeOperation;

    @Override
    public Uni<CompileSourceCodeHelpResponse> compile(final CompileSourceCodeHelpRequest request) {
        CompileSourceCodeHelpRequest.validate(request);

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    final var files = request.getFiles();
                    return files.stream()
                            .map(file -> {
                                final var fileName = file.getFileName();
                                final var sourceCode = file.getSourceCode();
                                final var bytecode = compileSourceCodeOperation.compileSourceCode(globals, fileName, sourceCode);
                                return bytecode;
                            })
                            .toList();
                })
                .map(CompileSourceCodeHelpResponse::new);
    }
}
