package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoMulticastCommandBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.MULTICAST;
    }

    @Override
    public DoCommandModel map(final LuaContext luaContext, LuaTable luaCommand) {
        final var luaClients = luaCommand.get("clients").checktable();
        final var clients = new ArrayList<Long>();
        for (int i = 1; i <= luaClients.length(); i++) {
            clients.add(Long.valueOf(luaClients.get(i).checkjstring()));
        }
        final var luaMessage = luaCommand.get("message").checktable();

        final var doCommandBody = new DoMulticastCommandBodyModel(clients, luaMessage);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_MULTICAST, doCommandBody);
        return doCommandModel;
    }
}
