package com.omgservers.application.module.internalModule.impl.service.jobInternalService.request;

import com.omgservers.application.module.internalModule.model.job.JobModel;
import com.omgservers.application.request.InternalRequest;
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
