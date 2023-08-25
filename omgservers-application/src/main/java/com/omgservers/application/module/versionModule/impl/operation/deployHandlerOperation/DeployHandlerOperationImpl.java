package com.omgservers.application.module.versionModule.impl.operation.deployHandlerOperation;

import com.omgservers.application.module.luaModule.LuaModule;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeployHandlerOperationImpl implements DeployHandlerOperation {

    final LuaModule handlerModule;

    @Override
    public Uni<Void> deployHandler(VersionModel version) {
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }

        return null;

//        final var sourceCode = decodeLuaCode(version);
//        final var compileRequest = new CompileSourceCodeRequest(sourceCode);
//        return handlerModule.getLuaHelpService().compileSourceCode(compileRequest)
//                .map(response -> {
//                    final var versionUuid = version.getUuid();
//                    final var bytecodeFiles = encodeBytecodeFiles(response.getFiles());
//                    final var handler = HandlerEntity.create(versionUuid, new VersionBytecodeModel(bytecodeFiles));
//                    return handler;
//                })
//                .flatMap(this::syncHandler)
//                .replaceWithVoid();

    }

//    List<LuaSourceCodeModel> decodeLuaCode(VersionEntity version) {
//        final var sourceCodeFiles = version.getSourceCode().getFiles().stream()
//                .filter(file -> file.getFileName().endsWith(".lua"))
//                .map(file -> {
//                    final var fileName = file.getFileName();
//                    final var base64Content = file.getBase64content();
//                    final var decodedContent = Base64.getDecoder().decode(base64Content);
//                    final var luaSourceCode = new String(decodedContent);
//                    return new LuaSourceCodeModel(fileName, luaSourceCode);
//                })
//                .toList();
//
//        return sourceCodeFiles;
//    }
//
//    List<HandlerFileModel> encodeBytecodeFiles(List<LuaBytecodeModel> files) {
//        final var handlerFiles = files.stream()
//                .map(file -> {
//                    final var fileName = file.getFileName();
//                    final var bytecode = file.getBytecode();
//                    final var encodedString = Base64.getEncoder().encodeToString(bytecode);
//                    return new HandlerFileModel(fileName, encodedString);
//                })
//                .toList();
//        return handlerFiles;
//    }
//
//    Uni<HandlerEntity> syncHandler(HandlerEntity handler) {
//        final var syncHandlerServiceRequest = new SyncHandlerServiceRequest(handler);
//        return handlerEntityService.syncHandler(syncHandlerServiceRequest)
//                .replaceWith(handler);
//    }
}
