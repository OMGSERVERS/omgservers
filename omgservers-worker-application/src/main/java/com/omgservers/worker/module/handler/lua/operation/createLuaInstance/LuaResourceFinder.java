package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.model.file.FileModel;
import org.luaj.vm2.lib.ResourceFinder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class LuaResourceFinder implements ResourceFinder {

    final Map<String, FileModel> resources;

    LuaResourceFinder(List<FileModel> files) {
        resources = new ConcurrentHashMap<>();
        files.forEach(file -> {
            final var fileName = file.getFileName();
            resources.put(fileName, file);
        });
    }

    @Override
    public InputStream findResource(String filename) {
        if (resources.containsKey(filename)) {
            final var bytes = resources.get(filename).getContent();
            return new ByteArrayInputStream(bytes);
        } else {
            throw new IllegalArgumentException(filename + " not found");
        }
    }
}
