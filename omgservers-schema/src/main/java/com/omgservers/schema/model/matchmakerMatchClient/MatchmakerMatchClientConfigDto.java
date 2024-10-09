package com.omgservers.schema.model.matchmakerMatchClient;

import com.omgservers.schema.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchClientConfigDto {

    static public MatchmakerMatchClientConfigDto create(final MatchmakerRequestModel request) {
        final var matchmakerMatchClientConfig = new MatchmakerMatchClientConfigDto();
        matchmakerMatchClientConfig.setRequest(request);
        return matchmakerMatchClientConfig;
    }

    @NotNull
    MatchmakerRequestModel request;
}
