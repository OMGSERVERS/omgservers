package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeRequest implements ShardedRequest {

    @NotNull
    Long fromUserId;

    @NotNull
    Long runtimeId;

    @NotNull
    List<OutgoingCommandModel> outgoingCommands;

    @NotNull
    List<Long> consumedCommands;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
