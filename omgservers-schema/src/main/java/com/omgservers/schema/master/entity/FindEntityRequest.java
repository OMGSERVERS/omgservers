package com.omgservers.schema.master.entity;

import com.omgservers.schema.master.MasterRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindEntityRequest implements MasterRequest {

    @NotNull
    Long entityId;
}
