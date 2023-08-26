package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.ShardRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncCommandShardRequest implements ShardRequest {

    static public void validate(SyncCommandShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    RuntimeCommandModel command;

    @Override
    public String getRequestShardKey() {
        return command.getRuntimeId().toString();
    }
}
