package com.omgservers.model.scriptEvent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ScriptEventDeserializer.class)
public class ScriptEventModel {

    public static void validate(ScriptEventModel scriptEvent) {
        if (scriptEvent == null) {
            throw new IllegalArgumentException("scriptEvent is null");
        }
    }

    ScriptEventQualifierEnum qualifier;
    @EqualsAndHashCode.Exclude
    ScriptEventBodyModel body;
}
