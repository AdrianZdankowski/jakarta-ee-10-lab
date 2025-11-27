package org.example.chat.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class ChatMessage {

    private String from;

    private String content;

    private String to;

}
