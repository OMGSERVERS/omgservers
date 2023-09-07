package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobRequest implements ShardedRequest {

    public static void validate(ScheduleJobRequest request) {
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
