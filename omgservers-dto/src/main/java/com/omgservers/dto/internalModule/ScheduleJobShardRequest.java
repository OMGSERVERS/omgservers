package com.omgservers.dto.internalModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobShardRequest implements ShardRequest {

    static public void validate(ScheduleJobShardRequest request) {
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
