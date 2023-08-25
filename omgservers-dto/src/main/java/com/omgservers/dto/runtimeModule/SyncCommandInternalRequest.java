package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.InternalRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncCommandInternalRequest implements InternalRequest {

    static public void validate(SyncCommandInternalRequest request) {
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
