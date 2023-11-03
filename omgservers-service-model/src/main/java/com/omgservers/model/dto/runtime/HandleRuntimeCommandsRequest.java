package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.event.EventModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleRuntimeCommandsRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    List<Long> ids;

    @NotNull
    List<EventModel> events;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
