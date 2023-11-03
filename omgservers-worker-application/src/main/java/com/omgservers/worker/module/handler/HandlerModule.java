package com.omgservers.worker.module.handler;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;

import java.util.List;

public interface HandlerModule {
    List<DoCommandModel> handleCommands(List<RuntimeCommandModel> runtimeCommands);
}
