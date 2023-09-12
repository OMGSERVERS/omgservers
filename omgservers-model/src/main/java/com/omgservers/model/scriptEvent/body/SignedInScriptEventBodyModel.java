package com.omgservers.model.scriptEvent.body;

import com.omgservers.model.scriptEvent.ScriptEventBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
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
public class SignedInScriptEventBodyModel extends ScriptEventBodyModel {

    Long userId;
    Long playerId;
    Long clientId;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.SIGNED_IN;
    }
}
