package com.omgservers.module.script.impl.operation.decodeLuaBytecode;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import com.omgservers.model.version.VersionFileModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
class DecodeLuaBytecodeOperationImpl implements DecodeLuaBytecodeOperation {

    @Override
    public List<LuaBytecodeModel> decodeLuaBytecode(List<VersionFileModel> files) {
        return files.stream()
                .map(file -> {
                    final var fileName = file.getFileName();
                    final var base64Content = file.getBase64content();
                    final var decodedBytes = Base64.getDecoder().decode(base64Content);
                    return new LuaBytecodeModel(fileName, decodedBytes);
                })
                .toList();
    }
}
