package org.example.push.context;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.example.push.dto.Message;

@ApplicationScoped
@Log
@NoArgsConstructor(force = true)
public class PushMessageContext {
    private PushContext broadcastChannel;
    private PushContext userChannel;

    @Inject
    public PushMessageContext(
            @Push(channel = "broadcastChannel") PushContext broadcastChannel,
            @Push(channel = "userChannel") PushContext userChannel
    ) {
        this.broadcastChannel = broadcastChannel;
        this.userChannel = userChannel;
    }

    public void notifyAll(Message message) {
        broadcastChannel.send(message);
    }

    public void notifyPilot(Message message, String pilotname) {
        userChannel.send(message, pilotname);
    }
}
