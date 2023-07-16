package com.omgservers.application.module.userModule.impl.service.attributeInternalService.response;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAttributeInternalResponse {

    AttributeModel attribute;
}
