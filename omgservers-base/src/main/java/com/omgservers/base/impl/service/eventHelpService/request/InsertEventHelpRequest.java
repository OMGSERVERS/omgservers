package com.omgservers.base.impl.service.eventHelpService.request;

import com.omgservers.model.event.EventBodyModel;
import io.vertx.mutiny.sqlclient.SqlConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertEventHelpRequest {

    static public void validate(InsertEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    SqlConnection sqlConnection;
    EventBodyModel eventBody;
}
