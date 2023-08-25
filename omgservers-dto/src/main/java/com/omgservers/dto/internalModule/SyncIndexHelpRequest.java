package com.omgservers.dto.internalModule;

import com.omgservers.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexHelpRequest {

    static public void validate(SyncIndexHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    IndexModel index;
}
