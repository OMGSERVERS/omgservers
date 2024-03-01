package com.omgservers.worker.module.handler.lua;

import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaInstance.LuaInstance;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.MapLuaCommandOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.List;

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
    public List<OutgoingCommandModel> handleCommands(List<RuntimeCommandModel> incomingCommands) {
        final var luaCommands = new ArrayList<LuaCommand>();
        incomingCommands.forEach(incomingCommand -> {
            try {
                final var luaCommand = mapRuntimeCommandOperation.mapRuntimeCommand(incomingCommand);
                luaCommands.add(luaCommand);
            } catch (Exception e) {
                log.warn("Map runtime command failed, qualifier={}, {}:{}",
                        incomingCommand.getQualifier(),
                        e.getClass().getSimpleName(),
                        e.getMessage());
            }
        });

        final var outgoingCommands = new ArrayList<OutgoingCommandModel>();
        for (final var luaCommand : luaCommands) {
            if (luaCommand.getInfoLogging()) {
                log.info("Handle, {}", luaCommand);
            }

            final var returnValue = luaInstance.call(luaCommand);
            if (returnValue != LuaValue.NIL) {
                final var returnTable = returnValue.checktable();
                final var resultCommands = parseReturnValue(returnTable);
                outgoingCommands.addAll(resultCommands.stream()
                        .map(mapLuaCommandOperation::mapLuaCommand)
                        .peek(outgoingCommand -> {
                            if (outgoingCommand.getQualifier().getInfoLogging()) {
                                log.info("Produce, {}", outgoingCommand.getBody());
                            }
                        })
                        .toList());
            }
        }

        return outgoingCommands;
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
