package com.omgservers.model.runtimeCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RuntimeCommandDeserializer.class)
public class RuntimeCommandModel {

    public static void validate(RuntimeCommandModel command) {
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long runtimeId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    RuntimeCommandQualifierEnum qualifier;

    @NotNull
    RuntimeCommandBodyModel body;

    @NotNull
    RuntimeCommandStatusEnum status;

    @NotNull
    Long step;
}
