package com.omgservers.service.server.index.dto;

import com.omgservers.schema.model.index.IndexModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexResponse {

    IndexModel index;
}
