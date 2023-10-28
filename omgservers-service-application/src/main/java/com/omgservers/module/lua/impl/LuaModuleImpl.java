package com.omgservers.module.lua.impl;

import com.omgservers.module.lua.LuaModule;
import com.omgservers.module.lua.impl.service.luaService.LuaService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LuaModuleImpl implements LuaModule {

    final LuaService luaService;

    public LuaService getLuaService() {
        return luaService;
    }
}
