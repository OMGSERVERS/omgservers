package com.omgservers.service.service.index.dto;

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