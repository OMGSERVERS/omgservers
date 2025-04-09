package com.omgservers.service.server.event.dto;

import com.omgservers.service.event.EventModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleEventRequest {

    @NotNull
    EventModel event;
}
