package com.omgservers.application.module.userModule.impl.service.attributeInternalService.response;

import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesInternalResponse {

    List<AttributeModel> attributes;
}
