package com.omgservers.schema.master.entity;

import com.omgservers.schema.master.MasterRequest;
import com.omgservers.schema.model.entity.EntityModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncEntityRequest implements MasterRequest {

    @NotNull
    EntityModel entity;
}
