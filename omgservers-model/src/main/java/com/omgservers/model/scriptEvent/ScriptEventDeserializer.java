package com.omgservers.model.scriptEvent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ScriptEventDeserializer extends StdDeserializer<ScriptEventModel> {

    public ScriptEventDeserializer() {
        this(null);
    }

    public ScriptEventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ScriptEventModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    ScriptEventModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var scriptEvent = new ScriptEventModel();

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = ScriptEventQualifierEnum.valueOf(qualifierNode.asText());
            scriptEvent.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                scriptEvent.setBody(body);
            }
        }

        return scriptEvent;
    }
}
