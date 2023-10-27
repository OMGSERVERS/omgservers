package com.omgservers.dto.internal;

import com.omgservers.model.index.IndexModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexOverServersRequest {

    @NotNull
    IndexModel index;

    @NotNull
    List<URI> servers;
}
