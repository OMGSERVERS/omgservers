package com.omgservers.module.version.impl.operation.compileVersionSourceCode;

import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import io.smallrye.mutiny.Uni;

public interface CompileVersionSourceCodeOperation {
    Uni<VersionBytecodeModel> compileVersionSourceCode(VersionSourceCodeModel sourceCode);
}
