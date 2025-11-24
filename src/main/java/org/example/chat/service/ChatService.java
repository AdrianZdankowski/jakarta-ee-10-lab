package org.example.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import org.example.chat.domain.ChatMessage;
import org.example.chat.event.ChatMessageEvent;

import java.util.logging.Level;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class ChatService {

    /**
     * Security context for getting current user.
     */
    private final SecurityContext securityContext;

    /**
     * Event for chat messages.
     */
    private final Event<ChatMessage> chatMessageEvent;

    /**
     * @param securityContext    security context
     * @param chatMessageEvent   event for chat messages
     */
    @Inject
    public ChatService(
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
            @ChatMessageEvent Event<ChatMessage> chatMessageEvent
    ) {
        this.securityContext = securityContext;
        this.chatMessageEvent = chatMessageEvent;
    }

    /**
     * Sends a chat message. If recipient is null or empty, message is broadcast to all users.
     *
     * @param content   message content
     * @param recipient recipient username (null or empty for broadcast)
     */
    public void sendMessage(String content, String recipient) {
        String sender = securityContext.getCallerPrincipal().getName();
        
        ChatMessage message = ChatMessage.builder()
                .from(sender)
                .content(content)
                .to(recipient)
                .build();

        chatMessageEvent.fire(message);
    }

}
