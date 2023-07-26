package com.omgservers.application.module.versionModule.impl.service.versionInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBytecodeInternalRequest implements InternalRequest {

    static public void validate(GetBytecodeInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long versionId;

    @Override
    public String getRequestShardKey() {
        return versionId.toString();
    }
}
