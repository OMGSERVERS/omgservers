package com.omgservers.schema.master.task;

import com.omgservers.schema.master.MasterRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskRequest implements MasterRequest {

    @NotNull
    Long id;
}
