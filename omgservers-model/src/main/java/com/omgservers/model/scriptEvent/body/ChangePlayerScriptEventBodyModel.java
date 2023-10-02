package com.omgservers.model.scriptEvent.body;

import com.omgservers.model.scriptEvent.ScriptEventBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChangePlayerScriptEventBodyModel extends ScriptEventBodyModel {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long clientId;

    @NotNull
    Object data;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.CHANGE_PLAYER;
    }
}
