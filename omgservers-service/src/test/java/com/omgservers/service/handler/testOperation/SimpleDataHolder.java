package com.omgservers.service.handler.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.module.tenant.operation.testOperation.TestVersionHolder;

public record SimpleDataHolder(
        TestVersionHolder testVersionHolder,
        RuntimeModel testRuntime,
        VersionLobbyRefModel testVersionRuntime,
        MatchmakerModel testMatchmaker,
        VersionMatchmakerRefModel testVersionMatchmaker,
        RuntimeClientModel testRuntimeClient) {
}
