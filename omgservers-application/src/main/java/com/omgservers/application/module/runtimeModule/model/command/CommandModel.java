package com.omgservers.application.module.runtimeModule.model.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = CommandDeserializer.class)
public class CommandModel {

    static public void validate(CommandModel command) {
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }
    }

    Long id;
    Long runtimeId;
    Instant created;
    Instant modified;
    CommandQualifierEnum qualifier;
    CommandBodyModel body;
    CommandStatusEnum status;
}
