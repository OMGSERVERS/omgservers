package com.omgservers.model.message;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class MessageDeserializer extends StdDeserializer<MessageModel> {

    public MessageDeserializer() {
        this(null);
    }

    public MessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MessageModel deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        try {
            return deserialize(parser);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    MessageModel deserialize(JsonParser parser) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);

        final var messageModel = new MessageModel();

        final var idNode = root.get("id");
        if (idNode != null) {
            messageModel.setId(idNode.asText());
        }

        final var qualifierNode = root.get("qualifier");
        if (qualifierNode != null) {
            final var qualifier = MessageQualifierEnum.valueOf(qualifierNode.asText());
            messageModel.setQualifier(qualifier);

            final var bodyNode = root.get("body");
            if (bodyNode != null) {
                final var body = mapper.treeToValue(bodyNode, qualifier.getBodyClass());
                messageModel.setBody(body);
            }
        }

        return messageModel;
    }
}
