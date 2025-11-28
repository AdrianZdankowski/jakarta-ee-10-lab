package org.example.chat.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.chat.service.ChatService;
import org.example.pilot.entity.Pilot;
import org.example.pilot.service.PilotService;

import java.util.List;
import java.util.logging.Level;

@Named
@RequestScoped
@NoArgsConstructor(force = true)
public class ChatView {
    
    private final ChatService chatService;

    private PilotService pilotService;

    @Getter
    @Setter
    private String messageContent;

    @Getter
    @Setter
    private String recipient;

    @Inject
    public ChatView(ChatService chatService) {
        this.chatService = chatService;
    }

    @EJB
    public void setPilotService(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    public List<Pilot> getPilots() {
        try {
            return pilotService.findAll();
        } catch (Exception e) {
            return List.of();
        }
    }

    public void sendMessage() {
        try {
            if (messageContent == null || messageContent.trim().isEmpty()) {
                return;
            }

            chatService.sendMessage(messageContent.trim(), recipient);
            
            messageContent = "";
            recipient = "";
            
        } catch (Exception e) {
        }
    }

}
