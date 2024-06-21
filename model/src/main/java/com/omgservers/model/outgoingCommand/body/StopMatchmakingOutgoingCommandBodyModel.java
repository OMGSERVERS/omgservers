package com.omgservers.model.outgoingCommand.body;

import com.omgservers.model.outgoingCommand.OutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopMatchmakingOutgoingCommandBodyModel extends OutgoingCommandBodyModel {

    @NotNull
    String reason;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.STOP_MATCHMAKING;
    }
}
