package com.omgservers.dto.internalModule;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnscheduleJobInternalRequest implements InternalRequest {

    static public void validate(UnscheduleJobInternalRequest request) {
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
