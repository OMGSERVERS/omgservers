package com.omgservers.model.scriptRequest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = ScriptRequestDeserializer.class)
public class ScriptRequestModel {

    public static void validate(ScriptRequestModel scriptEvent) {
        if (scriptEvent == null) {
            throw new IllegalArgumentException("scriptEvent is null");
        }
    }

    @NotNull
    ScriptRequestQualifierEnum qualifier;

    @NotNull
    @EqualsAndHashCode.Exclude
    ScriptRequestArgumentsModel arguments;
}
