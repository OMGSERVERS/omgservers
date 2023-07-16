package com.omgservers.application.module.internalModule.impl.service.producerHelpService.request;

import com.omgservers.application.module.internalModule.model.event.EventModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerEventHelpRequest {

    static public void validate(ProducerEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    EventModel event;
}
