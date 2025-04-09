package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;

public record HandleMatchmakerResult(Long matchmakerId,
                                     MatchmakerChangeOfStateDto matchmakerChangeOfState) {
}
