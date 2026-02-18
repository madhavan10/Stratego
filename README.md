This application has two parts or sub-applications
1. A server which is to be run by one of the players on his or her machine and
2. A client which is to be run by both players in order to play against each other.

There are 2 versions of this game
1. classical Stratego
2. Lord of the Rings Special Powers Stratego

1 is on branch master, 2 is on the branch special-powers

To run the game, first start the server using javac to compile all files and then 

java server.StrategoServer
Enter the number of minutes of setup time for each player in the pop-up box
The server will be listening 

Then connect the client by running
java stratego.Stratego
Enter the IP address of the server and connect

Two players are required to play so you can connect the client from another process on another computer (or the same computer if you wanna test)

After two players have joined the game starts after the first player chooses his team (ORC or HUMAN)

Enjoy



Feel free to browse...

