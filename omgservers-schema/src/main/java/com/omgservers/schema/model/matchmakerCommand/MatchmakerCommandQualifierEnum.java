package com.omgservers.schema.model.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerCommandQualifierEnum {
    KICK_CLIENT(KickClientMatchmakerCommandBodyDto.class),
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyDto.class),
    PREPARE_MATCH(PrepareMatchMatchmakerCommandBodyDto.class),
    EXCLUDE_MATCH(ExcludeMatchMatchmakerCommandBodyDto.class),
    DELETE_MATCH(DeleteMatchMatchmakerCommandBodyDto.class);

    final Class<? extends MatchmakerCommandBodyDto> bodyClass;
}
