package com.omgservers.utils.operation.bootstrapVersionOperation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.utils.model.VersionParameters;

public interface BootstrapVersionOperation {

    VersionParameters bootstrapVersion(String script) throws JsonProcessingException, InterruptedException;

    VersionParameters bootstrapVersion(String script, VersionConfigModel versionConfig) throws JsonProcessingException, InterruptedException;
}
