package com.omgservers.application.module.adminModule.impl.service.adminHelpService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollectLogsHelpRequest {

    static public void validate(CollectLogsHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    // TODO: add filters, sort
}
