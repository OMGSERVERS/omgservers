package com.omgservers.dto.matchmaker;

import com.omgservers.model.request.RequestModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequestsResponse {

    List<RequestModel> requests;
}
