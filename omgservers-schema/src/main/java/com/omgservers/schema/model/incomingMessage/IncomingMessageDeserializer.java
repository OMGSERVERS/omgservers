package com.omgservers.schema.model.incomingMessage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.omgservers.schema.message.MessageQualifierEnum;

import java.io.IOException;
import java.time.Instant;

public class IncomingMessageDeserializer extends StdDeserializer<IncomingMessageModel> {

    public IncomingMessageDeserializer() {
        this(null);
    }

    public IncomingMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public IncomingMessageModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    IncomingMessageModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var clientMessage = new IncomingMessageModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            clientMessage.setId(Long.valueOf(idNode.asText()));
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = MessageQualifierEnum.valueOf(qualifierNode.asText());
            clientMessage.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                clientMessage.setBody(body);
            }
        }

        return clientMessage;
    }
}
