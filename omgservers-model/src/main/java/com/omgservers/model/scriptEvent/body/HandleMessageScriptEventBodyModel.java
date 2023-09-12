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
public class HandleMessageScriptEventBodyModel extends ScriptEventBodyModel {

    Long userId;
    Long playerId;
    Long clientId;

    String message;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.HANDLE_MESSAGE;
    }
}
