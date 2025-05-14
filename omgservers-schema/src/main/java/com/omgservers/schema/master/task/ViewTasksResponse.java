package com.omgservers.schema.master.task;

import com.omgservers.schema.model.task.TaskModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTasksResponse {

    List<TaskModel> tasks;
}
