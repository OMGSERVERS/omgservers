package com.omgservers.schema.master.task;

import com.omgservers.schema.master.MasterRequest;
import com.omgservers.schema.model.task.TaskModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTaskRequest implements MasterRequest {

    @NotNull
    TaskModel task;
}
