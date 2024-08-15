package com.omgservers.service.service.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelayEventsResponse {

    // True if at least on event was handled and handling can be requests immediately again
    Boolean result;
}
