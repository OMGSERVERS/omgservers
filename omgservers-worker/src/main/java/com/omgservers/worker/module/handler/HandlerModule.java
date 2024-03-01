package com.omgservers.worker.module.handler;

import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;

import java.util.List;

public interface HandlerModule {

    List<OutgoingCommandModel> handleCommands(List<RuntimeCommandModel> incomingCommands);
}
