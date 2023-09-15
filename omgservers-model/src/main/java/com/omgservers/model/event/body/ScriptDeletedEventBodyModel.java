package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.script.ScriptModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ScriptDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    ScriptModel script;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SCRIPT_DELETED;
    }

    @Override
    public Long getGroupId() {
        return script.getId();
    }
}
