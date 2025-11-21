package org.example.chat.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.example.chat.domain.ChatMessage;
import org.example.chat.event.ChatMessageEvent;
import org.example.push.context.PushMessageContext;
import org.example.push.dto.Message;

import java.util.logging.Level;

/**
 * Observer implementation for chat messages. It will send messages to appropriate users via WebSocket.
 */
@ApplicationScoped
@Log
public class ChatMessageObserver {

    /**
     * Context for sending push messages.
     */
    private final PushMessageContext pushMessageContext;

    /**
     * @param pushMessageContext context for sending push messages
     */
    @Inject
    public ChatMessageObserver(PushMessageContext pushMessageContext) {
        this.pushMessageContext = pushMessageContext;
    }

    /**
     * Observer method for chat messages. Called automatically when {@link ChatMessageEvent} is fired.
     *
     * @param chatMessage chat message to be sent
     */
    public void processChatMessage(@Observes @ChatMessageEvent ChatMessage chatMessage) {
        log.log(Level.INFO, "Processing chat message: " + chatMessage.toString());
        
        Message message = Message.builder()
                .from(chatMessage.getFrom())
                .content(chatMessage.getContent())
                .build();

        if (chatMessage.getTo() == null || chatMessage.getTo().isEmpty()) {
            // Broadcast to all users
            pushMessageContext.notifyAll(message);
        } else {
            // Send to specific user
            pushMessageContext.notifyPilot(message, chatMessage.getTo());
        }
    }

}
