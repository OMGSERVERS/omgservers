package com.omgservers.application.module.userModule.impl.service.objectInternalService.request;

import com.omgservers.application.module.userModule.model.object.ObjectModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncObjectInternalRequest implements InternalRequest {

    static public void validate(SyncObjectInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID user;
    ObjectModel object;

    @Override
    public String getRequestShardKey() {
        return user.toString();
    }
}
