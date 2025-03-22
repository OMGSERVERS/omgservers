package com.omgservers.service.service.registry.dto;

import java.util.List;
import java.util.Objects;

public record ParsedResourceScope(RegistryResourceTypeEnum resourceType,
                                  String namespace,
                                  String image,
                                  List<RegistryActionEnum> actions) {

    public String buildResourceName() {
        if (Objects.isNull(namespace)) {
            return image;
        } else {
            return String.format("%s/%s", namespace, image);
        }
    }
}
