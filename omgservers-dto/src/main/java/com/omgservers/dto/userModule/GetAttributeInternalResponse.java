package com.omgservers.dto.userModule;

import com.omgservers.model.attribute.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttributeInternalResponse {

    AttributeModel attribute;
}
