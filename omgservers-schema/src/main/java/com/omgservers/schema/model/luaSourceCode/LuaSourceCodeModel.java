package com.omgservers.schema.model.luaSourceCode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuaSourceCodeModel {

    @NotBlank
    String fileName;

    @NotBlank
    String sourceCode;
}
