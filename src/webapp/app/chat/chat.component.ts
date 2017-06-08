import {Component, OnInit, OnDestroy} from '@angular/core';
import {ChatService} from "./chat.service";


@Component({
  selector: 'chat-component',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  public messages: Array<any>;
  public chatBox: string;

  public constructor(private socket: ChatService) {
    this.messages = [];
    this.chatBox = "";
  }

  public ngOnInit() {
    this.socket.connect();
    this.socket.getEventListener().subscribe(event => {
      if (event.type == "message") {
        let data = event.data;
        if (event.data.sender) {
          data = event.data.sender + ": " + data;
        }
        this.messages.push(data);
      }
      if (event.type == "close") {
        console.log('hurrrr',event);
        if (event.data.code === 1007) {
          this.messages.push("/You are already connected");
        } else {
          this.messages.push("/You have been disconnected");
        }
      }
      if (event.type == "open") {
        this.messages.push("/You have successfully connected to the chat");
      }
    });
  }

  public ngOnDestroy() {
    this.socket.close();
  }

  public send() {
    if (this.chatBox) {
      this.socket.send(this.chatBox);
      this.chatBox = "";
    }
  }

  public isSystemMessage(message: string) {
    return message.startsWith("/") ? "<strong>" + message.substring(1) + "</strong>" : message;
  }
}
