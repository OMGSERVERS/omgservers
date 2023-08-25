package com.omgservers.base.impl.service.eventHelpService.request;

import com.omgservers.model.event.EventBodyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEventHelpRequest {

    static public void validate(FireEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    EventBodyModel eventBody;
}
