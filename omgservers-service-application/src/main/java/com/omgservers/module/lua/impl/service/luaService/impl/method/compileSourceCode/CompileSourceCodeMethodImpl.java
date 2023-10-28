package com.omgservers.module.lua.impl.service.luaService.impl.method.compileSourceCode;

import com.omgservers.model.dto.lua.CompileSourceCodeRequest;
import com.omgservers.model.dto.lua.CompileSourceCodeResponse;
import com.omgservers.module.lua.impl.operation.compileSourceCode.CompileSourceCodeOperation;
import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class CompileSourceCodeMethodImpl implements CompileSourceCodeMethod {

    final CreateServerGlobalsOperation createServerGlobalsOperation;
    final CompileSourceCodeOperation compileSourceCodeOperation;

    @Override
    public Uni<CompileSourceCodeResponse> compile(final CompileSourceCodeRequest request) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    final var files = request.getFiles();
                    return files.stream()
                            .map(file -> {
                                final var fileName = file.getFileName();
                                final var sourceCode = file.getSourceCode();
                                final var bytecode =
                                        compileSourceCodeOperation.compileSourceCode(globals, fileName, sourceCode);
                                return bytecode;
                            })
                            .toList();
                })
                .map(CompileSourceCodeResponse::new);
    }
}
