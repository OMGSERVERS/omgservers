package com.omgservers.model.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleEventsResponse {

    // True - if at least on event was handled and handling can be requests immediately again
    Boolean result;
}
