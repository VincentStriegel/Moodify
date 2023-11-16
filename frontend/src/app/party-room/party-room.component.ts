import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-party-room',
  templateUrl: './party-room.component.html',
  styleUrls: ['./party-room.component.css']
})
export class PartyRoomComponent {
  title = 'WebSocketClient';
  song = "";
  query: string;

  private webSocket: WebSocket;

  constructor(private route: ActivatedRoute) {
    this.query = this.route.snapshot.paramMap.get('roomId') || '';
    this.webSocket = new WebSocket('ws://localhost:8080/party-room/' + this.query);
    this.webSocket.onmessage = (event) => {
    //this.song = JSON.parse(event.data)
      this.song = event.data;
    };
  }
  sendSong(){
    console.log("sending song");
    this.webSocket.send("song");
  } 
}
