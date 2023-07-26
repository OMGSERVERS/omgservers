package com.omgservers.application.module.internalModule.impl.service.eventHelpService.request;

import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import io.vertx.mutiny.sqlclient.SqlConnection;
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
