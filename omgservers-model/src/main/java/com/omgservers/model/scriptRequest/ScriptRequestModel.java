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

    @NotNull
    ScriptRequestQualifierEnum qualifier;

    @NotNull
    @EqualsAndHashCode.Exclude
    ScriptRequestArgumentsModel arguments;
}
