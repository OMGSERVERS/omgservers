package com.omgservers.application.module.internalModule.impl.service.logHelpService.request;

import com.omgservers.application.module.internalModule.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLogHelpRequest {

    static public void validate(SyncLogHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    LogModel log;
}
