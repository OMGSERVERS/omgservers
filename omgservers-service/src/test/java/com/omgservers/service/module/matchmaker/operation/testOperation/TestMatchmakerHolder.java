package com.omgservers.service.module.matchmaker.operation.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;

public record TestMatchmakerHolder(MatchmakerModel matchmaker,
                                   VersionMatchmakerModel versionMatchmaker) {
}
