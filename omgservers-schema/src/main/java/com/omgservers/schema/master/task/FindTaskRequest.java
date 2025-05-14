package com.omgservers.schema.master.task;

import com.omgservers.schema.master.MasterRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTaskRequest implements MasterRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;
}
