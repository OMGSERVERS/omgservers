package com.omgservers.schema.master.entity;

import com.omgservers.schema.model.entity.EntityModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewEntitiesResponse {

    List<EntityModel> entities;
}
