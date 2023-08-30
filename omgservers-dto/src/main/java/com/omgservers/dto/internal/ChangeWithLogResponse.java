package com.omgservers.dto.internal;

import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeWithLogResponse {

    Boolean result;
    ExtendedResponse extendedResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendedResponse {
        LogModel changeLog;
    }
}
