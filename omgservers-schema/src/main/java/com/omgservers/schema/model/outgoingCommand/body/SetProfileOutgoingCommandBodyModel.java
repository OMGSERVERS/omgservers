package com.omgservers.schema.model.outgoingCommand.body;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SetProfileOutgoingCommandBodyModel extends OutgoingCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    Object profile;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.SET_PROFILE;
    }
}
