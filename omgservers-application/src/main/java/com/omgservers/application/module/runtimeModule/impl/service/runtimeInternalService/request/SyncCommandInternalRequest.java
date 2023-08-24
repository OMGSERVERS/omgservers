package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request;

import com.omgservers.application.InternalRequest;
import com.omgservers.application.module.runtimeModule.model.command.CommandModel;
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

    CommandModel command;

    @Override
    public String getRequestShardKey() {
        return command.getRuntimeId().toString();
    }
}
