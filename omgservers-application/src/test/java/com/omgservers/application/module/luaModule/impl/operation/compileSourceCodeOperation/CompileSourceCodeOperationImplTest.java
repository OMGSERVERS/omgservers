package com.omgservers.application.module.luaModule.impl.operation.compileSourceCodeOperation;

import com.omgservers.application.module.luaModule.impl.operation.createServerGlobalsOperation.CreateServerGlobalsOperation;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class CompileSourceCodeOperationImplTest extends Assertions {

    @Inject
    CompileSourceCodeOperation compileSourceCodeOperation;

    @Inject
    CreateServerGlobalsOperation createServerGlobalsOperation;

    @Test
    void whenCompileSourceCode() {
        final var globals = createServerGlobalsOperation.createServerGlobals();
        final var bytes = compileSourceCodeOperation
                .compileSourceCode(globals, "stub", "print('helloworld')");
        assertNotNull(bytes);
    }
}