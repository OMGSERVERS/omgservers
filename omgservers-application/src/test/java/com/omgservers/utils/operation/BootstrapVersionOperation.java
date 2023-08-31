package com.omgservers.utils.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.model.version.VersionStageConfigModel;

public interface BootstrapVersionOperation {

    VersionParameters bootstrapVersion(String script) throws JsonProcessingException, InterruptedException;

    VersionParameters bootstrapVersion(String script, VersionStageConfigModel stageConfig) throws JsonProcessingException, InterruptedException;
}
