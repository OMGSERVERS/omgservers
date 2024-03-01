package com.omgservers.worker.component.workerJobTask;

import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@ApplicationScoped
class WorkerJobState {

    final List<OutgoingCommandModel> outgoingCommands;
    final List<Long> consumedCommands;

    public WorkerJobState() {
        this.outgoingCommands = new CopyOnWriteArrayList<>();
        this.consumedCommands = new CopyOnWriteArrayList<>();
    }

    public List<OutgoingCommandModel> getOutgoingCommands() {
        return outgoingCommands;
    }

    public void setOutgoingCommands(final List<OutgoingCommandModel> outgoingCommands) {
        this.outgoingCommands.clear();
        this.outgoingCommands.addAll(outgoingCommands);
    }

    public List<Long> getConsumedCommands() {
        return consumedCommands;
    }

    public void setConsumedCommands(final List<Long> consumedCommands) {
        this.consumedCommands.clear();
        this.consumedCommands.addAll(consumedCommands);
    }
}
