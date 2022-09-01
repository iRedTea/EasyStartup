function connect() {
    let socket = new SockJs(`api/socket`);

    let stompClient = Stomp.over(socket);

    return stompClient;
}


let stompClient = this.webSocketService.connect();

stompClient.connect({}, frame => {

  stompClient.subscribe('/user/queue/notification', notification => {
      console.log('test'+ notification)
      JSON.parse(notification.body);
  })     
});


 const sendMessage = (msg) => {
    if (msg.trim() !== "") {
      const message = {
        senderId: currentUser.id,
        recipientId: activeContact.id,
        senderName: currentUser.name,
        recipientName: activeContact.name,
        content: msg,
      };
        
      stompClient.send("/app/chat", {}, JSON.stringify(message));
    }
  };

