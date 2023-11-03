package com.omgservers.model.doCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = DoCommandDeserializer.class)
public class DoCommandModel {

    @NotNull
    DoCommandQualifierEnum qualifier;

    @NotNull
    DoCommandBodyModel body;
}
