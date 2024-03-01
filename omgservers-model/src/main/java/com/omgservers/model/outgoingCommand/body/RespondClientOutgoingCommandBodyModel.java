package com.omgservers.model.outgoingCommand.body;

import com.omgservers.model.outgoingCommand.OutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
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
public class RespondClientOutgoingCommandBodyModel extends OutgoingCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    Object message;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.RESPOND_CLIENT;
    }
}
