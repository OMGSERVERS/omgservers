package com.omgservers.dto.user;

import com.omgservers.model.attribute.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttributeShardedResponse {

    AttributeModel attribute;
}
