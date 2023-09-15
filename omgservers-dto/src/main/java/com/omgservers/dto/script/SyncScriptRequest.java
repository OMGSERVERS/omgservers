package com.omgservers.dto.script;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.script.ScriptModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncScriptRequest implements ShardedRequest {

    @NotNull
    ScriptModel script;

    @Override
    public String getRequestShardKey() {
        return script.getId().toString();
    }
}
