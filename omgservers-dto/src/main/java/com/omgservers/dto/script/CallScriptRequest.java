package com.omgservers.dto.script;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.scriptEvent.ScriptEventModel;
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
    List<ScriptEventModel> events;

    @Override
    public String getRequestShardKey() {
        return scriptId.toString();
    }
}
