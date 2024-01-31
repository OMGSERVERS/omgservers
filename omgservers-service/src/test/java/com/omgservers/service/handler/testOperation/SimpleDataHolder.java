package com.omgservers.service.handler.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.module.tenant.operation.testOperation.TestVersionHolder;

public record SimpleDataHolder(
        TestVersionHolder testVersionHolder,
        RuntimeModel testRuntime,
        VersionRuntimeModel testVersionRuntime,
        MatchmakerModel testMatchmaker,
        VersionMatchmakerModel testVersionMatchmaker,
        RuntimeClientModel testRuntimeClient) {
}
