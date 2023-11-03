package com.omgservers.worker.module.handler.lua;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.luaRequest.impl.UpdateRuntimeLuaRequest;
import com.omgservers.worker.module.handler.lua.operation.createLuaInstance.LuaInstance;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.MapLuaCommandOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LuaHandlerModuleImpl implements HandlerModule {

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
    final MapLuaCommandOperation mapLuaCommandOperation;
    final LuaInstance luaInstance;

    public LuaHandlerModuleImpl(final MapRuntimeCommandOperation mapRuntimeCommandOperation,
                                final MapLuaCommandOperation mapLuaCommandOperation,
                                final LuaInstance luaInstance) {
        this.mapRuntimeCommandOperation = mapRuntimeCommandOperation;
        this.mapLuaCommandOperation = mapLuaCommandOperation;
        this.luaInstance = luaInstance;
    }

    @Override
    public List<DoCommandModel> handleCommands(final List<RuntimeCommandModel> runtimeCommands) {
        final var luaRequests = runtimeCommands.stream()
                .map(mapRuntimeCommandOperation::mapRuntimeCommand)
                .collect(Collectors.toList());

        // Add update command automatically
        final var updateRuntimeLuaRequest = new UpdateRuntimeLuaRequest(Instant.now().toEpochMilli());
        luaRequests.add(updateRuntimeLuaRequest);

        final var doCommands = new ArrayList<DoCommandModel>();
        for (final var luaRequest : luaRequests) {
            if (luaRequest.getInfoLogging()) {
                log.info("Handle, {}", luaRequest);
            }

            final var returnValue = luaInstance.call(luaRequest);
            if (returnValue != LuaValue.NIL) {
                final var returnTable = returnValue.checktable();
                final var luaCommands = parseReturnValue(returnTable);
                doCommands.addAll(luaCommands.stream()
                        .map(mapLuaCommandOperation::mapLuaCommand)
                        .peek(doCommand -> {
                            if (doCommand.getQualifier().getInfoLogging()) {
                                log.info("Produce, {}", doCommand.getBody());
                            }
                        })
                        .toList());
            }
        }

        return doCommands;
    }

    List<LuaTable> parseReturnValue(LuaTable returnValue) {
        final var results = new ArrayList<LuaTable>();

        var k = LuaValue.NIL;
        while (true) {
            final var n = returnValue.next(k);
            k = n.arg1();
            if (k.isnil()) {
                break;
            }
            final var v = n.arg(2).checktable();

            results.add(v);
        }

        return results;
    }
}
