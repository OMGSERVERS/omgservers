package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request;

import com.omgservers.application.module.luaModule.model.LuaSourceCodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileSourceCodeHelpRequest {

    static public void validate(CompileSourceCodeHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    List<LuaSourceCodeModel> files;
}
