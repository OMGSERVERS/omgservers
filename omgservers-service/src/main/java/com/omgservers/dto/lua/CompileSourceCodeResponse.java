package com.omgservers.dto.lua;

import com.omgservers.model.luaBytecode.LuaBytecodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileSourceCodeResponse {

    List<LuaBytecodeModel> files;
}
