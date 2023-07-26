package com.omgservers.application.module.versionModule.impl.service.versionInternalService.request;

import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionInternalRequest implements InternalRequest {

    static public void validate(SyncVersionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    VersionModel version;

    @Override
    public String getRequestShardKey() {
        return version.getId().toString();
    }
}
