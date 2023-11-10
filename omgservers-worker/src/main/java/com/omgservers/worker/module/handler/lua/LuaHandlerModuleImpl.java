package com.omgservers.worker.module.handler.lua;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.workerContext.WorkerContextModel;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.UpdateRuntimeLuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaInstance.LuaInstance;
import com.omgservers.worker.module.handler.lua.operation.createLuaContext.CreateLuaContextOperation;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.MapLuaCommandOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LuaHandlerModuleImpl implements HandlerModule {

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
    final CreateLuaContextOperation createLuaContextOperation;
    final MapLuaCommandOperation mapLuaCommandOperation;

    final LuaInstance luaInstance;

    public LuaHandlerModuleImpl(final MapRuntimeCommandOperation mapRuntimeCommandOperation,
                                final CreateLuaContextOperation createLuaContextOperation,
                                final MapLuaCommandOperation mapLuaCommandOperation,
                                final LuaInstance luaInstance) {
        this.mapRuntimeCommandOperation = mapRuntimeCommandOperation;
        this.createLuaContextOperation = createLuaContextOperation;
        this.mapLuaCommandOperation = mapLuaCommandOperation;
        this.luaInstance = luaInstance;
    }

    @Override
    public List<DoCommandModel> handleCommands(final WorkerContextModel workerContext) {
        final var luaContext = createLuaContextOperation.createLuaContext(workerContext);

        final var runtimeCommands = workerContext.getRuntimeCommands();
        final var luaCommands = new ArrayList<LuaCommand>();
        runtimeCommands.forEach(runtimeCommand -> {
            try {
                final var luaCommand = mapRuntimeCommandOperation.mapRuntimeCommand(luaContext, runtimeCommand);
                luaCommands.add(luaCommand);
            } catch (Exception e) {
                log.warn("Map runtime command failed, qualifier={}, {}:{}",
                        runtimeCommand.getQualifier(),
                        e.getClass().getSimpleName(),
                        e.getMessage());
            }
        });

        // Add update command automatically
        final var updateRuntimeLuaRequest = new UpdateRuntimeLuaCommand(Instant.now().toEpochMilli());
        luaCommands.add(updateRuntimeLuaRequest);

        final var doCommands = new ArrayList<DoCommandModel>();
        for (final var luaCommand : luaCommands) {
            if (luaCommand.getInfoLogging()) {
                log.info("Handle, {}", luaCommand);
            }

            final var returnValue = luaInstance.call(luaCommand);
            if (returnValue != LuaValue.NIL) {
                final var returnTable = returnValue.checktable();
                final var resultCommands = parseReturnValue(returnTable);
                doCommands.addAll(resultCommands.stream()
                        .map(resultCommand -> mapLuaCommandOperation.mapLuaCommand(luaContext, resultCommand))
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
