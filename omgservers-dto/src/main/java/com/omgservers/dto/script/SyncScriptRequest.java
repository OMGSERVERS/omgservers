package com.omgservers.dto.script;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.script.ScriptModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncScriptRequest implements ShardedRequest {

    public static void validate(SyncScriptRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    ScriptModel script;

    @Override
    public String getRequestShardKey() {
        return script.getId().toString();
    }
}
