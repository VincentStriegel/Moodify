package com.moodify.backend.api.transferobjects;

import com.moodify.backend.domain.services.MessageType;
import org.springframework.web.socket.TextMessage;

public class PartyRoomWebSocketTO {

    private MessageType messageType;

    private Object data;

    public PartyRoomWebSocketTO(MessageType messageType, Object data) {
        this.messageType = messageType;
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
