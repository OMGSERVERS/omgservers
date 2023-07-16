package com.omgservers.application.module.versionModule.impl.operation.compileVersionSourceCodeOperation;

import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CompileSourceCodeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CompileSourceCodeHelpResponse;
import com.omgservers.application.module.luaModule.model.LuaBytecodeModel;
import com.omgservers.application.module.luaModule.model.LuaSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import com.omgservers.application.module.versionModule.model.VersionFileModel;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
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

    final LuaModule handlerModule;

    @Override
    public Uni<VersionBytecodeModel> compileVersionSourceCode(VersionSourceCodeModel sourceCode) {
        final var luaSourceCodeFiles = decodeLuaSourceCode(sourceCode.getFiles());
        final var compileSourceCodeRequest = new CompileSourceCodeHelpRequest(luaSourceCodeFiles);
        return handlerModule.getRuntimeHelpService().compileSourceCode(compileSourceCodeRequest)
                .map(CompileSourceCodeHelpResponse::getFiles)
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
