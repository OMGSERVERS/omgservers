package com.omgservers.dto.internal;

import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLogRequest {

    static public void validate(SyncLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    LogModel log;
}
