package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.InternalRequest;
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
