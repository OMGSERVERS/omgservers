package com.omgservers.service.server.task.dto;

import com.omgservers.service.server.task.TaskResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteDispatcherTaskResponse {

    TaskResult taskResult;
}
