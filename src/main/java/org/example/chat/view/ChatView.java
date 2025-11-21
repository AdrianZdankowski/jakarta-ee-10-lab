package org.example.chat.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.example.chat.service.ChatService;
import org.example.pilot.entity.Pilot;
import org.example.pilot.service.PilotService;

import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for chat functionality.
 */
@Named
@RequestScoped
@Log
@NoArgsConstructor(force = true)
public class ChatView {

    /**
     * Chat service.
     */
    private final ChatService chatService;

    /**
     * Pilot service for getting list of users.
     */
    private PilotService pilotService;

    /**
     * Message content to send.
     */
    @Getter
    @Setter
    private String messageContent;

    /**
     * Recipient username (empty or null for broadcast).
     */
    @Getter
    @Setter
    private String recipient;

    /**
     * @param chatService chat service
     */
    @Inject
    public ChatView(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * @param pilotService pilot service
     */
    @EJB
    public void setPilotService(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    /**
     * Gets list of all pilots for recipient selection.
     * 
     * @return list of all pilots
     */
    public List<Pilot> getPilots() {
        try {
            return pilotService.findAll();
        } catch (Exception e) {
            log.log(Level.WARNING, "Could not fetch pilots list", e);
            return List.of();
        }
    }

    /**
     * Sends a chat message via AJAX.
     */
    public void sendMessage() {
        try {
            if (messageContent == null || messageContent.trim().isEmpty()) {
                log.log(Level.WARNING, "Cannot send empty message");
                return;
            }

            chatService.sendMessage(messageContent.trim(), recipient);
            
            // Clear the form after sending
            messageContent = "";
            recipient = "";
            
            log.log(Level.INFO, "Message sent successfully");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error sending message", e);
        }
    }

}
