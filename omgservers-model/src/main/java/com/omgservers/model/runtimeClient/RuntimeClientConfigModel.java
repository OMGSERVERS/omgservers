package com.omgservers.model.runtimeClient;

import com.omgservers.model.matchClient.MatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeClientConfigModel {

    static public RuntimeClientConfigModel create() {
        final var runtimeClientConfig = new RuntimeClientConfigModel();
        return runtimeClientConfig;
    }

    MatchClientModel matchClient;
}
