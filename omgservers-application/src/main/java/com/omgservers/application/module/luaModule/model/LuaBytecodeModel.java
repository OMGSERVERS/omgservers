package com.omgservers.application.module.luaModule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuaBytecodeModel {

    String fileName;
    byte[] bytecode;
}
