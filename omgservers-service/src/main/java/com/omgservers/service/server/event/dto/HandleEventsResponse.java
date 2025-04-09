package com.omgservers.service.server.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleEventsResponse {

    // True if at least one event was selected, and handling should be requested again immediately.
    Boolean result;
}
