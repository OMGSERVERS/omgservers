package com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.internalModule.model.job.JobType;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobInternalRequest implements InternalRequest {

    static public void validate(ScheduleJobInternalRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
    }

    Long shardKey;
    Long entity;
    JobType type;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
