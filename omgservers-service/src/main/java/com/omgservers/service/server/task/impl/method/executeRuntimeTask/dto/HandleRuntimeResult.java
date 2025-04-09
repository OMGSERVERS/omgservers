package com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.runtimeChangeOfState.RuntimeChangeOfStateDto;

import java.util.List;

public record HandleRuntimeResult(Long runtimeId,
                                  List<DeploymentCommandModel> deploymentCommandsToSync,
                                  List<MatchmakerCommandModel> matchmakerCommandsToSync,
                                  List<Long> clientsToDelete,
                                  RuntimeChangeOfStateDto runtimeChangeOfState) {
}
