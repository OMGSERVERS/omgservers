package com.omgservers.schema.model.matchmakerCommand.body;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RemoveClientMatchmakerCommandBodyDto extends MatchmakerCommandBodyDto {

    @NotNull
    Long clientId;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.REMOVE_CLIENT;
    }
}
