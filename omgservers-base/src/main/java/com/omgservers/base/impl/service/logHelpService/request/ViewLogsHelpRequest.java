package com.omgservers.base.impl.service.logHelpService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewLogsHelpRequest {

    static public void validate(ViewLogsHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }
}
