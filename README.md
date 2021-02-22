# Pong-Online  
Game written in Java using [LibGDX](https://libgdx.com/). 

### What's this?  
This is a multiplayer clone of classic game of Pong.  
Server-side of this project supports multiple sessions of the game at the same time with lobby feature, which lets player choose a game to connect.  
  
### How's it working?
All of the client-server interactions is built upon custom high-level TCP protocol, which uses serialization courtasy of [Gson library](https://github.com/google/gson) and Java reflections to send commands through the network as serialized objects.  
This protocol is capable of sending data from one side of connection to another. It is able to send "executable code", while keeping connection safe. This is possible by sending class names through the networkinsted of code and use sent data to restore an object of this class.
In this way, you can decide from one side what code you want to execute on another, but it'd be executed if and only if other side of your connection has a class, code of which you want to execute.  
And the whole point about sanitizing received data comes to keeping track of what types of command are included into your application.

### Where can I find more information about this game and network protocol?
You can find [video](https://youtu.be/qF7x4BOLTm8) about creation process of this game on [my YouTube channel](https://www.youtube.com/channel/UCIZ2dFv95v8V61fqsn35PQA), where I'm describing everything about it in more details.
