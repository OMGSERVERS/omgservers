package com.omgservers.model.dto.system;

import com.omgservers.model.entitiy.EntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindEntityResponse {

    EntityModel entity;
}
