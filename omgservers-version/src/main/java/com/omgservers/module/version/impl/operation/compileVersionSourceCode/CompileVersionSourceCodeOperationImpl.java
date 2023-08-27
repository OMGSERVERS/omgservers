package com.omgservers.module.version.impl.operation.compileVersionSourceCode;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import com.omgservers.model.luaSourceCode.LuaSourceCodeModel;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionFileModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.module.lua.LuaModule;
import com.omgservers.dto.lua.CompileSourceCodeRequest;
import com.omgservers.dto.lua.CompileSourceCodeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CompileVersionSourceCodeOperationImpl implements CompileVersionSourceCodeOperation {

    final LuaModule luaModule;

    @Override
    public Uni<VersionBytecodeModel> compileVersionSourceCode(VersionSourceCodeModel sourceCode) {
        final var luaSourceCodeFiles = decodeLuaSourceCode(sourceCode.getFiles());
        final var compileSourceCodeRequest = new CompileSourceCodeRequest(luaSourceCodeFiles);
        return luaModule.getLuaService().compileSourceCode(compileSourceCodeRequest)
                .map(CompileSourceCodeResponse::getFiles)
                .map(this::encodeBytecodeFiles)
                .map(VersionBytecodeModel::new);
    }

    List<LuaSourceCodeModel> decodeLuaSourceCode(List<VersionFileModel> sourceCode) {
        final var sourceCodeFiles = sourceCode.stream()
                .map(file -> {
                    final var fileName = file.getFileName();
                    final var base64Content = file.getBase64content();
                    final var decodedContent = Base64.getDecoder().decode(base64Content);
                    final var luaSourceCode = new String(decodedContent);
                    return new LuaSourceCodeModel(fileName, luaSourceCode);
                })
                .toList();

        return sourceCodeFiles;
    }

    List<VersionFileModel> encodeBytecodeFiles(List<LuaBytecodeModel> files) {
        final var versionByteCodeFiles = files.stream()
                .map(file -> {
                    final var fileName = file.getFileName();
                    final var bytecode = file.getBytecode();
                    final var encodedString = Base64.getEncoder().encodeToString(bytecode);
                    return new VersionFileModel(fileName, encodedString);
                })
                .toList();

        return versionByteCodeFiles;
    }
}
