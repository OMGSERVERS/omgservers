package com.omgservers.model.runtimeCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.exception.ServerSideBadRequestException;
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

    Long id;
    Long runtimeId;
    Instant created;
    Instant modified;
    RuntimeCommandQualifierEnum qualifier;
    RuntimeCommandBodyModel body;
    RuntimeCommandStatusEnum status;
    Long step;
}
