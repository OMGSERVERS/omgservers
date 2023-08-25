package com.omgservers.dto.internalModule;

import com.omgservers.model.job.JobModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncJobInternalRequest implements InternalRequest {

    static public void validate(SyncJobInternalRequest request) {
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
