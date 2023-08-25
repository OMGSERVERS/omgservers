package com.omgservers.application.module.luaModule.impl.operation.compileSourceCodeOperation;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import org.luaj.vm2.Globals;

public interface CompileSourceCodeOperation {
    LuaBytecodeModel compileSourceCode(Globals globals, String fileName, String sourceCode);
}
