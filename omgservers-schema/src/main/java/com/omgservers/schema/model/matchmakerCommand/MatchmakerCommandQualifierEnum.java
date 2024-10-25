package com.omgservers.schema.model.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.body.CloseMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.OpenMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.RemoveClientMatchmakerCommandBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerCommandQualifierEnum {
    KICK_CLIENT(KickClientMatchmakerCommandBodyDto.class),
    REMOVE_CLIENT(RemoveClientMatchmakerCommandBodyDto.class),
    OPEN_MATCH(OpenMatchMatchmakerCommandBodyDto.class),
    CLOSE_MATCH(CloseMatchMatchmakerCommandBodyDto.class),
    DELETE_MATCH(DeleteMatchMatchmakerCommandBodyDto.class);

    final Class<? extends MatchmakerCommandBodyDto> bodyClass;
}
