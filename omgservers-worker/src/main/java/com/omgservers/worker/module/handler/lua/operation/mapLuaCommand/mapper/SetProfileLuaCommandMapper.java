package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoSetProfileCommandBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetProfileLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.SET_PROFILE;
    }

    @Override
    public DoCommandModel map(final LuaContext luaContext, LuaTable luaCommand) {
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());

        final var luaProfile = luaCommand.get("profile").checktable();
        luaContext.updateProfile(clientId, luaProfile);

        final var doCommandBody = new DoSetProfileCommandBodyModel(clientId, luaProfile);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_SET_PROFILE, doCommandBody);
        return doCommandModel;
    }
}
