package com.omgservers.dto.runtime;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRuntimeCommandRequest implements ShardedRequest {

    public static void validate(DeleteRuntimeCommandRequest request) {
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