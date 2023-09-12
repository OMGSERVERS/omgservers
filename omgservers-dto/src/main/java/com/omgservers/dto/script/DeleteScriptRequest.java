package com.omgservers.dto.script;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteScriptRequest implements ShardedRequest {

    public static void validate(DeleteScriptRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
