package com.omgservers.dto.internal;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.event.EventModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEventRequest implements ShardedRequest {

    @NotNull
    EventModel event;

    @Override
    public String getRequestShardKey() {
        return event.getGroupId().toString();
    }
}
