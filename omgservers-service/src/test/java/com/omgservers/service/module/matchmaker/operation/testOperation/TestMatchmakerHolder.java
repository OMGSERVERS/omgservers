package com.omgservers.service.module.matchmaker.operation.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;

public record TestMatchmakerHolder(MatchmakerModel matchmaker,
                                   VersionMatchmakerRefModel versionMatchmaker) {
}
