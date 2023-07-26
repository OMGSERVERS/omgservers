package com.omgservers.application.module.internalModule.impl.service.eventInternalService.request;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEventInternalRequest implements InternalRequest {

    static public void validate(FireEventInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    EventModel event;

    @Override
    public String getRequestShardKey() {
        return event.getGroupId().toString();
    }
}
