package org.example.chat.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.example.chat.domain.ChatMessage;
import org.example.chat.event.ChatMessageEvent;
import org.example.push.context.PushMessageContext;
import org.example.push.dto.Message;

import java.util.logging.Level;

@ApplicationScoped
public class ChatMessageObserver {

    private final PushMessageContext pushMessageContext;

    @Inject
    public ChatMessageObserver(PushMessageContext pushMessageContext) {
        this.pushMessageContext = pushMessageContext;
    }

    public void processChatMessage(@Observes @ChatMessageEvent ChatMessage chatMessage) {
        Message message = Message.builder()
                .from(chatMessage.getFrom())
                .content(chatMessage.getContent())
                .build();

        if (chatMessage.getTo() == null || chatMessage.getTo().isEmpty()) {
            pushMessageContext.notifyAll(message);
        } else {
            pushMessageContext.notifyPilot(message, chatMessage.getTo());
            if (!chatMessage.getFrom().equals(chatMessage.getTo())) {
                pushMessageContext.notifyPilot(message, chatMessage.getFrom());
            }
        }
    }

}
