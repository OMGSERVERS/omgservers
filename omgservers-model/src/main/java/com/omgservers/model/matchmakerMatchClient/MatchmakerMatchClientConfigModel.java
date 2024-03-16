package com.omgservers.model.matchmakerMatchClient;

import com.omgservers.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchClientConfigModel {

    static public MatchmakerMatchClientConfigModel create(MatchmakerRequestModel request) {
        final var config = new MatchmakerMatchClientConfigModel();
        config.setRequest(request);
        return config;
    }

    @NotNull
    MatchmakerRequestModel request;
}
