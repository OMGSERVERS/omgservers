package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;

public record HandleMatchmakerResult(Long matchmakerId,
                                     MatchmakerChangeOfStateDto matchmakerChangeOfState) {
}
