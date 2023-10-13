package com.omgservers.dto.internal;

import com.omgservers.model.event.EventModel;
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