package service;

import io.smallrye.reactive.messaging.amqp.OutgoingAmqpMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import model.RequestDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.jboss.logging.Logger;

import java.util.Map;
import java.util.stream.IntStream;

import static org.eclipse.microprofile.reactive.messaging.OnOverflow.Strategy.UNBOUNDED_BUFFER;

@ApplicationScoped
public class RequestService {

    private static final Logger log = Logger.getLogger(RequestService.class);

    @Channel("requests-out")
    @OnOverflow(UNBOUNDED_BUFFER)
    Emitter<String> consumerMessageEmitter;

    public void sendMessages(RequestDTO requestDTO) {
        log.info("Sending messages");
        IntStream.range(0, requestDTO.numberOfMessages()).parallel().forEach(i -> {
            consumerMessageEmitter.send(Message.of(buildMessage(requestDTO, i)).addMetadata(buildMetadata(requestDTO.amqpMetadata())));
        });
        log.info("Messages sent to requests queue");
    }

    private String buildMessage(RequestDTO requestDTO, int i) {
        String message = requestDTO.message();
        String messageParameter = requestDTO.messageParameters().get(i % requestDTO.messageParameters().size());
        return message.replace("{{placeholder}}",  messageParameter);
    }

    private OutgoingAmqpMetadata buildMetadata(Map<String, String> inputMetadata) {
        if (inputMetadata == null) {
            return null;
        }
        OutgoingAmqpMetadata.OutgoingAmqpMetadataBuilder builder = OutgoingAmqpMetadata.builder();
        inputMetadata.forEach(builder::withApplicationProperty);
        return builder.build();
    }

}
