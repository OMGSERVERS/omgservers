package com.omgservers.dto.internalModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.job.JobModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncJobShardRequest implements ShardRequest {

    static public void validate(SyncJobShardRequest request) {
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
