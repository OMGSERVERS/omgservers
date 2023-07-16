package com.omgservers.application.module.versionModule.impl.operation.compileVersionSourceCodeOperation;

import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import io.smallrye.mutiny.Uni;

public interface CompileVersionSourceCodeOperation {
    Uni<VersionBytecodeModel> compileVersionSourceCode(VersionSourceCodeModel sourceCode);
}
