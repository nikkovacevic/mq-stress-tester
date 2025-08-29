package model;

import java.util.List;
import java.util.Map;

public record RequestDTO(
        Integer numberOfMessages,
        Map<String, String> amqpMetadata,
        String message,
        List<String> messageParameters
) {
}
