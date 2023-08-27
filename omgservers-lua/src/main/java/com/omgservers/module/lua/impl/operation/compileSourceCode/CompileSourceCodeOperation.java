package com.omgservers.module.lua.impl.operation.compileSourceCode;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import org.luaj.vm2.Globals;

public interface CompileSourceCodeOperation {
    LuaBytecodeModel compileSourceCode(Globals globals, String fileName, String sourceCode);
}
