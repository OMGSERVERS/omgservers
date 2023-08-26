package com.omgservers.dto.internalModule;

import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewLogsResponse {

    List<LogModel> logs;
}
