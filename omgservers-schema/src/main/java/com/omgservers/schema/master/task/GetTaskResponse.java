package com.omgservers.schema.master.task;

import com.omgservers.schema.model.task.TaskModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskResponse {

    TaskModel task;
}
