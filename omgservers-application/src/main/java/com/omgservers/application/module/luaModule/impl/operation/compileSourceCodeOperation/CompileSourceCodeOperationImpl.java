package com.omgservers.application.module.luaModule.impl.operation.compileSourceCodeOperation;

import com.omgservers.application.module.luaModule.model.LuaBytecodeModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Print;
import org.luaj.vm2.compiler.DumpState;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@ApplicationScoped
class CompileSourceCodeOperationImpl implements CompileSourceCodeOperation {

    @Override
    public LuaBytecodeModel compileSourceCode(final Globals globals,
                                              final String fileName,
                                              final String sourceCode) {
        try {
            final var inputStream = new ByteArrayInputStream(sourceCode.getBytes(StandardCharsets.UTF_8));
            final var prototype = globals.compilePrototype(inputStream, fileName);
            Print.print(prototype);
            final var outputStream = new ByteArrayOutputStream();
            DumpState.dump(prototype, outputStream, false);
            final var bytecode = outputStream.toByteArray();
            final var compiledLuaScript = new LuaBytecodeModel(fileName, bytecode);
            return compiledLuaScript;
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
