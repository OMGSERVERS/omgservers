package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.worker.module.handler.lua.component.luaInstance.LuaInstance;
import com.omgservers.worker.operation.decodeFiles.DecodeFilesOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateLuaInstanceOperationImpl implements CreateLuaInstanceOperation {

    final DecodeFilesOperation decodeFilesOperation;

    @Override
    public LuaInstance createLuaInstance(final VersionSourceCodeModel versionSourceCode) {
        final var globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
//        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
//        globals.load(new JseIoLib());
//        globals.load(new JseOsLib());
//        globals.load(new LuajavaLib());
        LoadState.install(globals);
        LuaC.install(globals);
        final var decodedFiles = decodeFilesOperation.decodeFiles(versionSourceCode.getFiles());
        globals.finder = new LuaResourceFinder(decodedFiles);
        globals.set("print", new LuaPrintFunction(globals));
        // TODO: determine filename
        return new LuaInstance(globals, "main.lua");
    }
}
