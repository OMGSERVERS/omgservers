package com.omgservers.application.module.luaModule.impl.operation.decodeLuaBytecodeOperation;

import com.omgservers.application.module.luaModule.model.LuaBytecodeModel;
import com.omgservers.application.module.versionModule.model.VersionFileModel;

import java.util.List;

public interface DecodeLuaBytecodeOperation {
    List<LuaBytecodeModel> decodeLuaBytecode(List<VersionFileModel> files);
}
