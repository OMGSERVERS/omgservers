package com.omgservers.dto.internalModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnscheduleJobShardRequest implements ShardRequest {

    static public void validate(UnscheduleJobShardRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
    }

    Long shardKey;
    Long entity;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
