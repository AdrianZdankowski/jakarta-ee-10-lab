package org.example.chat.domain;

import lombok.*;

/**
 * Represents a chat message that can be sent to all users or to a specific user.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class ChatMessage {

    /**
     * Sender's username.
     */
    private String from;

    /**
     * Message content.
     */
    private String content;

    /**
     * Receiver's username. If null, message is broadcast to all users.
     */
    private String to;

}
