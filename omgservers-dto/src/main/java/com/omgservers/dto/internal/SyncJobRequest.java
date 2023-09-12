package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.job.JobModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncJobRequest implements ShardedRequest {

    public static void validate(SyncJobRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    JobModel job;

    @Override
    public String getRequestShardKey() {
        return job.getShardKey().toString();
    }
}