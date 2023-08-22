package com.omgservers.application.module.internalModule.impl.service.jobInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteJobInternalRequest implements InternalRequest {

    static public void validate(DeleteJobInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long shardKey;
    Long entity;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}
