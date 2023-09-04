package com.omgservers.dto.lua;

import com.omgservers.model.luaSourceCode.LuaSourceCodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileSourceCodeRequest {

    public static void validate(CompileSourceCodeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    List<LuaSourceCodeModel> files;
}
