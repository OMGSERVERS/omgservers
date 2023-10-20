package com.omgservers.dto.script;

import com.omgservers.model.script.ScriptModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetScriptResponse {

    ScriptModel script;
}
