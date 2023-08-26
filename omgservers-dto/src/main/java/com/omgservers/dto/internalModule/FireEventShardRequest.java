package com.omgservers.dto.internalModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.event.EventModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEventShardRequest implements ShardRequest {

    static public void validate(FireEventShardRequest request) {
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
