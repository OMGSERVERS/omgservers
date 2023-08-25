package com.omgservers.dto.internalModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteJobInternalRequest implements InternalRequest {

    static public void validate(DeleteJobInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long shardKey;
    Long entity;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
