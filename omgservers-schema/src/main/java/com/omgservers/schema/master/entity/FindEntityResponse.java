package com.omgservers.schema.master.entity;

import com.omgservers.schema.model.entity.EntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindEntityResponse {

    EntityModel entity;
}
