package com.omgservers.schema.master.index;

import com.omgservers.schema.master.MasterRequest;
import com.omgservers.schema.model.index.IndexModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIndexRequest implements MasterRequest {

    @NotNull
    IndexModel index;
}
