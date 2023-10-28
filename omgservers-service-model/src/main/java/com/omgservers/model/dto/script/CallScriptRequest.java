package com.omgservers.model.dto.script;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallScriptRequest implements ShardedRequest {

    @NotNull
    Long scriptId;

    @NotNull
    List<ScriptRequestModel> requests;

    @Override
    public String getRequestShardKey() {
        return scriptId.toString();
    }
}
