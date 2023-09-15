package com.omgservers.model.luaBytecode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuaBytecodeModel {

    @NotBlank
    String fileName;

    @NotNull
    @NotEmpty
    byte[] bytecode;
}
