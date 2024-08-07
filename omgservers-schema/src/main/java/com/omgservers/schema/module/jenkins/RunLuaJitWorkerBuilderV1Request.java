package com.omgservers.schema.module.jenkins;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunLuaJitWorkerBuilderV1Request {

    @NotNull
    String groupId;

    @NotNull
    String containerName;

    @NotNull
    String versionId;

    @NotNull
    String base64Archive;
}
