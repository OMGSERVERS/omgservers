package com.omgservers.module.context.impl.operation.decodeLuaBytecode;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import com.omgservers.model.version.VersionFileModel;

import java.util.List;

public interface DecodeLuaBytecodeOperation {
    List<LuaBytecodeModel> decodeLuaBytecode(List<VersionFileModel> files);
}
