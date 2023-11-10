package com.omgservers.worker.module.handler;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.workerContext.WorkerContextModel;

import java.util.List;

public interface HandlerModule {
    List<DoCommandModel> handleCommands(WorkerContextModel workerContext);
}
