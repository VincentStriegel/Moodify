import { Component } from '@angular/core';
import { WebSocketService } from '../services/web-socket.service';

@Component({
  selector: 'app-party-room',
  templateUrl: './party-room.component.html',
  styleUrls: ['./party-room.component.css']
})
export class PartyRoomComponent {
  title = 'WebSocketClient';


  private webSocket: WebSocket;

  constructor() {
    this.webSocket = new WebSocket('ws://localhost:8080/party-room');
    this.webSocket.onmessage = (event) => {
      //JSON.parse(event.data)
    };
  } 
}
