package com.omgservers.schema.model.outgoingCommand.body;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopMatchmakingOutgoingCommandBodyDto extends OutgoingCommandBodyDto {

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.STOP_MATCHMAKING;
    }
}
