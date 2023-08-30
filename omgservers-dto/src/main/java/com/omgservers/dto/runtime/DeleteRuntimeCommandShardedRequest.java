package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRuntimeCommandShardedRequest implements ShardedRequest {

    static public void validate(DeleteRuntimeCommandShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long runtimeId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}