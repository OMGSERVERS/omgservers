package com.omgservers.dto.script;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallScriptRequest implements ShardedRequest {

    public static void validate(CallScriptRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long scriptId;
    List<ScriptEventModel> events;

    @Override
    public String getRequestShardKey() {
        return scriptId.toString();
    }
}
