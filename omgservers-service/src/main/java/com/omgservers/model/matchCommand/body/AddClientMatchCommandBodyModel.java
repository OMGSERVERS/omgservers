package com.omgservers.model.matchCommand.body;

import com.omgservers.model.matchCommand.MatchCommandBodyModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddClientMatchCommandBodyModel extends MatchCommandBodyModel {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.ADD_CLIENT;
    }
}
