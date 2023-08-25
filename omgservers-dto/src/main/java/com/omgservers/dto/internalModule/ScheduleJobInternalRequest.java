package com.omgservers.dto.internalModule;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.job.JobType;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
