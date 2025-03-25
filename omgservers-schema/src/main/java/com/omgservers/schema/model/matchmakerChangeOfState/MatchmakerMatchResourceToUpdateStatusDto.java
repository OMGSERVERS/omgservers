package com.omgservers.schema.model.matchmakerChangeOfState;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import jakarta.validation.constraints.NotNull;

public record MatchmakerMatchResourceToUpdateStatusDto(@NotNull Long id,
                                                       @NotNull MatchmakerMatchResourceStatusEnum status) {
}
