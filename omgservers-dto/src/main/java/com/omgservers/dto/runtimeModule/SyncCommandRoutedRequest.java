package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncCommandRoutedRequest implements RoutedRequest {

    static public void validate(SyncCommandRoutedRequest request) {
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
