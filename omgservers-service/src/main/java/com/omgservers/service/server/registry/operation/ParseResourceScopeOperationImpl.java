package com.omgservers.service.server.registry.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.server.registry.dto.ParsedResourceScope;
import com.omgservers.service.server.registry.dto.RegistryActionEnum;
import com.omgservers.service.server.registry.dto.RegistryResourceTypeEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseResourceScopeOperationImpl implements ParseResourceScopeOperation {

    static private final int TYPE_PARSING = 0;
    static private final int NAMESPACE_PARSING = 3;
    static private final int IMAGE_PARSING = 4;
    static private final int ACTION_PARSING = 5;

    @Override
    public ParsedResourceScope execute(final String scope) {

        // repository:tenant/project:pull,push

        String type = null;
        String namespace = null;
        String image = null;

        List<String> actions = new ArrayList<>();

        int s = TYPE_PARSING;
        char c;
        StringBuilder w = new StringBuilder();
        int length = scope.length();
        for (int i = 0; i < length; i++) {
            c = scope.charAt(i);

            switch (s) {
                case TYPE_PARSING:
                    if (c == ':') {
                        type = w.toString();
                        w.setLength(0);
                        s = NAMESPACE_PARSING;
                    } else {
                        w.append(c);
                    }
                    break;
                case NAMESPACE_PARSING:
                    if (c == '/') {
                        namespace = w.toString();
                        w.setLength(0);
                        s = IMAGE_PARSING;
                    } else if (c == ':') {
                        image = w.toString();
                        w.setLength(0);
                        s = ACTION_PARSING;
                    } else {
                        w.append(c);
                    }
                    break;
                case IMAGE_PARSING:
                    if (c == ':') {
                        image = w.toString();
                        w.setLength(0);
                        s = ACTION_PARSING;
                    } else {
                        w.append(c);
                    }
                    break;
                case ACTION_PARSING:
                    if (c == ',') {
                        actions.add(w.toString());
                        w.setLength(0);
                    } else {
                        w.append(c);
                    }
                    break;
            }
        }

        // Last action
        if (s == ACTION_PARSING && !w.isEmpty()) {
            actions.add(w.toString());
        }

        if (type == null || image == null || actions.isEmpty()) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_SCOPE);
        }

        final RegistryResourceTypeEnum registryResourceType;
        final List<RegistryActionEnum> registryActions;
        try {
            registryResourceType = RegistryResourceTypeEnum.fromString(type);
            registryActions = actions.stream()
                    .map(RegistryActionEnum::fromString)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_SCOPE);
        }

        return new ParsedResourceScope(registryResourceType,
                namespace,
                image,
                registryActions);
    }
}
