package com.omgservers.application.module.runtimeModule.model.command.body;

import com.omgservers.application.module.runtimeModule.model.command.CommandBodyModel;
import com.omgservers.application.module.runtimeModule.model.command.CommandQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IncomingCommandBodyModel extends CommandBodyModel {

    Long userId;
    Long clientId;
    String body;

    @Override
    public CommandQualifierEnum getQualifier() {
        return CommandQualifierEnum.INCOMING;
    }
}
