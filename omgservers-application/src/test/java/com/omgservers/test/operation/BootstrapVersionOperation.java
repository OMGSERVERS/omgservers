package com.omgservers.test.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.model.version.VersionStageConfigModel;

public interface BootstrapVersionOperation {

    void bootstrapVersion(String script) throws JsonProcessingException, InterruptedException;

    void bootstrapVersion(String script, VersionStageConfigModel stageConfig) throws JsonProcessingException, InterruptedException;
}
