package com.omgservers.application.module.runtimeModule.model.command.body;

import com.omgservers.application.module.runtimeModule.model.command.CommandBodyModel;
import com.omgservers.application.module.runtimeModule.model.command.CommandQualifierEnum;
import com.omgservers.application.module.versionModule.model.VersionModeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StartCommandBodyModel extends CommandBodyModel {

    VersionModeModel mode;

    @Override
    public CommandQualifierEnum getQualifier() {
        return CommandQualifierEnum.START;
    }
}
