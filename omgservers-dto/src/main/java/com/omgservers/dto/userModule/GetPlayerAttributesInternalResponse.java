package com.omgservers.dto.userModule;

import com.omgservers.model.attribute.AttributeModel;
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
