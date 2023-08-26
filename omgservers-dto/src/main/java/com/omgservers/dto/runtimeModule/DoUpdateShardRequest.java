package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoUpdateShardRequest implements ShardRequest {

    static public void validate(DoUpdateShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long runtimeId;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
