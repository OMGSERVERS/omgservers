package com.omgservers.utils.operation.deleteVersionOperation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.omgservers.utils.model.VersionParameters;

public interface DeleteVersionOperation {

    Boolean deleteVersion(VersionParameters versionParameters) throws JsonProcessingException;
}
