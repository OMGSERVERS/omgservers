package com.omgservers.dto.internal;

import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLogRequest {

    public static void validate(SyncLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    LogModel log;
}
