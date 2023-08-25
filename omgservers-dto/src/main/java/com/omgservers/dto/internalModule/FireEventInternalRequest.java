package com.omgservers.dto.internalModule;

import com.omgservers.model.event.EventModel;
import com.omgservers.dto.InternalRequest;
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
