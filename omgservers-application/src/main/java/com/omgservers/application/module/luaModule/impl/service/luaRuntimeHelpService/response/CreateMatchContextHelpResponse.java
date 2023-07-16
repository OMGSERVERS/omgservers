package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.match.LuaMatchContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchContextHelpResponse {

    LuaMatchContext luaMatchContext;
}
