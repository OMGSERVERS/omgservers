package com.omgservers.schema.model.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerCommandQualifierEnum {
    KICK_CLIENT(KickClientMatchmakerCommandBodyModel.class),
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyModel.class),
    PREPARE_MATCH(PrepareMatchMatchmakerCommandBodyModel.class),
    EXCLUDE_MATCH(ExcludeMatchMatchmakerCommandBodyModel.class),
    DELETE_MATCH(DeleteMatchMatchmakerCommandBodyModel.class);

    final Class<? extends MatchmakerCommandBodyModel> bodyClass;
}
