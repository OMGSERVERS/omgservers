package com.omgservers.model.scriptEvent.body;

import com.omgservers.model.scriptEvent.ScriptEventBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopScriptEventBodyModel extends ScriptEventBodyModel {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.STOP;
    }
}
