package com.omgservers.dto.internalModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobRoutedRequest implements RoutedRequest {

    static public void validate(ScheduleJobRoutedRequest request) {
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
