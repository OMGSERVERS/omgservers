package com.omgservers.model.dto.lua;

import com.omgservers.model.luaSourceCode.LuaSourceCodeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileSourceCodeRequest {

    @NotNull
    List<LuaSourceCodeModel> files;
}
