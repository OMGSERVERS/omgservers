package com.omgservers.base.impl.service.handlerHelpService.request;

import com.omgservers.model.event.EventModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleEventHelpRequest {

    static public void validate(HandleEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    EventModel event;
}
