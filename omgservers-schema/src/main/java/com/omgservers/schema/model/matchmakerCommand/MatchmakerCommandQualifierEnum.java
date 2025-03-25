package com.omgservers.schema.model.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.body.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerCommandQualifierEnum {
    OPEN_MATCH(OpenMatchMatchmakerCommandBodyDto.class),
    CLOSE_MATCH(CloseMatchMatchmakerCommandBodyDto.class),
    DELETE_MATCH(DeleteMatchMatchmakerCommandBodyDto.class),
    KICK_CLIENT(KickClientMatchmakerCommandBodyDto.class),
    REMOVE_CLIENT(RemoveClientMatchmakerCommandBodyDto.class);

    final Class<? extends MatchmakerCommandBodyDto> bodyClass;
}
