package com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface HandleOutgoingMessageOperation {
    Uni<Void> execute(RuntimeModel runtime,
                      List<RuntimeAssignmentModel> runtimeAssignments,
                      MessageModel outgoingMessage);
}
