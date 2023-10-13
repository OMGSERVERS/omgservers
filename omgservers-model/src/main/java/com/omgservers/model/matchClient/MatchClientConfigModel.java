package com.omgservers.model.matchClient;

import com.omgservers.model.request.RequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchClientConfigModel {

    static public MatchClientConfigModel create(RequestModel request) {
        final var config = new MatchClientConfigModel();
        config.setRequest(request);
        return config;
    }

    @NotNull
    RequestModel request;
}
