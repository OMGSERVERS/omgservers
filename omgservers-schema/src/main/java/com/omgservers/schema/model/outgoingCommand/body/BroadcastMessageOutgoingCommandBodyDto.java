package com.omgservers.schema.model.outgoingCommand.body;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BroadcastMessageOutgoingCommandBodyDto extends OutgoingCommandBodyDto {

    @NotNull
    @ToString.Exclude
    Object message;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.BROADCAST_MESSAGE;
    }
}
