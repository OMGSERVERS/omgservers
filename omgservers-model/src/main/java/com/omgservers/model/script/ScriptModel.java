package com.omgservers.model.script;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptModel {

    public static void validate(ScriptModel matchClient) {
        if (matchClient == null) {
            throw new ServerSideBadRequestException("matchClient is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    Long tenantId;
    Long versionId;
    ScriptType type;
    String self;
    ScriptConfigModel config;
}
