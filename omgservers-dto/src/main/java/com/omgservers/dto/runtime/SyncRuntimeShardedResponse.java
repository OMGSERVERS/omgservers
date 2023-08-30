package com.omgservers.dto.runtime;

import com.omgservers.dto.internal.ChangeWithEventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRuntimeShardedResponse {

    Boolean created;
    ExtendedResponse extendedResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendedResponse {
        ChangeWithEventResponse.ExtendedResponse changeExtendedResponse;
    }
}
