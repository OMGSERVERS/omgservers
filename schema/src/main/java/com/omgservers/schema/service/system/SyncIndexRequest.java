package com.omgservers.schema.service.system;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.schema.model.index.IndexModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexRequest {

    @NotNull
    IndexModel index;
}
