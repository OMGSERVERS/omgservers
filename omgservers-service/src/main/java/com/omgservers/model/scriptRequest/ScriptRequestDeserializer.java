package com.omgservers.model.scriptRequest;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ScriptRequestDeserializer extends StdDeserializer<ScriptRequestModel> {

    public ScriptRequestDeserializer() {
        this(null);
    }

    public ScriptRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ScriptRequestModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    ScriptRequestModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var scriptEvent = new ScriptRequestModel();

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = ScriptRequestQualifierEnum.valueOf(qualifierNode.asText());
            scriptEvent.setQualifier(qualifier);

            final var argumentsNode = root.get("arguments");
            if (argumentsNode != null) {
                final var arguments = mapper.treeToValue(argumentsNode, qualifier.getArgumentsClass());
                scriptEvent.setArguments(arguments);
            }
        }

        return scriptEvent;
    }
}
