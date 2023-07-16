package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.LuaRuntime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLuaRuntimeHelpResponse {

    LuaRuntime luaRuntime;
}
