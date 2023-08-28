package com.omgservers.dto.runtime;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoRuntimeUpdateShardedResponse {

    Integer handledCommands;
    ExtendedResponse extendedResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendedResponse {
        List<RuntimeCommandModel> affectedCommands;
    }
}
