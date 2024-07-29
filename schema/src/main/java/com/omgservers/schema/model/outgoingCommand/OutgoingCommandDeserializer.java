package com.omgservers.schema.model.outgoingCommand;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OutgoingCommandDeserializer extends StdDeserializer<OutgoingCommandModel> {

    public OutgoingCommandDeserializer() {
        this(null);
    }

    public OutgoingCommandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OutgoingCommandModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    OutgoingCommandModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var commandModel = new OutgoingCommandModel();

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = OutgoingCommandQualifierEnum.valueOf(qualifierNode.asText());
            commandModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                commandModel.setBody(body);
            }
        }

        return commandModel;
    }
}
