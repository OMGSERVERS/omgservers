package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.job.JobTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobRequest implements ShardedRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;

    @NotNull
    JobTypeEnum type;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
