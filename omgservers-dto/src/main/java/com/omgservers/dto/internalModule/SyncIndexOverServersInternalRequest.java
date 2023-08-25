package com.omgservers.dto.internalModule;

import com.omgservers.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexOverServersInternalRequest {

    static public void validateSyncIndexOverServersInternalRequest(SyncIndexOverServersInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    IndexModel index;
    List<URI> servers;
}
