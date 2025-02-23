package com.omgservers.schema.model.outgoingCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = OutgoingCommandDeserializer.class)
public class OutgoingCommandModel {

    @NotNull
    OutgoingCommandQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    OutgoingCommandBodyDto body;
}
