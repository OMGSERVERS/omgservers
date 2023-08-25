package com.omgservers.base.impl.service.logHelpService.response;

import com.omgservers.model.log.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewLogsHelpResponse {

    List<LogModel> logs;
}
