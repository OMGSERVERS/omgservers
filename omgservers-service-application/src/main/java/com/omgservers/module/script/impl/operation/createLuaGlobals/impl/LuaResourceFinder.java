package com.omgservers.module.script.impl.operation.createLuaGlobals.impl;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import org.luaj.vm2.lib.ResourceFinder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class LuaResourceFinder implements ResourceFinder {

    final Map<String, LuaBytecodeModel> resources;

    LuaResourceFinder(List<LuaBytecodeModel> files) {
        resources = new ConcurrentHashMap<>();
        files.forEach(model -> {
            final var fileName = model.getFileName();
            resources.put(fileName, model);
        });
    }

    @Override
    public InputStream findResource(String filename) {
        if (resources.containsKey(filename)) {
            final var bytecode = resources.get(filename).getBytecode();
            return new ByteArrayInputStream(bytecode);
        } else {
            throw new ServerSideConflictException(filename + " not found");
        }
    }
}
