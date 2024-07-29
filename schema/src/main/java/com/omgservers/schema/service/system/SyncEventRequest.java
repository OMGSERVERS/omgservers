package com.omgservers.schema.service.system;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncEventRequest {

    @NotNull
    EventModel event;
}
