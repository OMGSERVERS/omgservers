package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommandShardRequest implements ShardRequest {

    static public void validate(DeleteCommandShardRequest request) {
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
