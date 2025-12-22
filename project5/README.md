# World Project
### Network LAN Multiplayer functionality
#### Made by Sean & Marco @UCB


## How to play multiplayer
1. Run ifconfig or ipconfig depending on your OS to find your IP address of the computer that is hosting the game (i.e. has the source code - in other words computerA is both the host and the client).
2. Press M on the StdDraw console
3. Let the other player (computerB) run this command to connect to the Multiplayer Lobby
   ```bash
   nc <ip-of-computerA> <port> # replace ip-of-computerA with the output from step 1, and port with 25565.
    ```
4. Enter seed (need to press on the StdDraw console) to generate world

## How to play single player
1. N > Seed > Collect coins > `:q` to load and save

## How to load single player
1. L > continue playing game

## Bugs

1. `player.java`
- I encountered a bug when the user will try to move around in multiplayer, so I removed it from GameLogic.runMultiGame. Below is the code:
  ```java
      public void movePlayer(playerObj player, int x, int y) {
        if (x == 0) {
            int tempY = player.cords[1] + y;
            if (!isWallOOB(player.cords[0], tempY)) {
                // if legal, then modify reference and avatar
                if (isCoin(player.cords[0], tempY)) {
                   player.coinsCollected++;
                }
                prevPosToFloor(player.cords[0], player.cords[1]);
                player.cords[1] += y;
                worldTiles[player.cords[0]][player.cords[1]] = Tileset.AVATAR;
            } else {
                // player is eating other player, thus we have them move y + y so no conflict
                int newTempY = player.cords[1] + y + y;
                if (!isWallOOB(player.cords[0], newTempY)) {
                    if (isCoin(player.cords[0], newTempY)) {
                        player.coinsCollected++;
                    }
                    prevPosToFloor(player.cords[0], player.cords[1]);
                    player.cords[1] = newTempY;
                    worldTiles[player.cords[0]][player.cords[1]] = Tileset.AVATAR;
                }
            }
        } else if (y == 0) {
            int tempX = player.cords[0] + x;
            if (!isWallOOB(tempX, player.cords[1])) {
                // if legal, then modify reference and avatar
                if (isCoin(tempX, player.cords[1])) {
                    player.coinsCollected++;
                }
                prevPosToFloor(player.cords[0], player.cords[1]);
                player.cords[0] += x;
                worldTiles[player.cords[0]][player.cords[1]] = Tileset.AVATAR;
            } else {
                // player is eating other player, thus we have them move x + x so no conflict
                int newTempX = player.cords[0] + x + x;
                if (!isWallOOB(newTempX, player.cords[1])) {
                    if (isCoin(newTempX, player.cords[1])) {
                        player.coinsCollected++;
                        }
                    prevPosToFloor(player.cords[0], player.cords[1]);
                    player.cords[0] = newTempX;
                    worldTiles[player.cords[0]][player.cords[1]] = Tileset.AVATAR;
                }
            }
        }
   }


    ```
  
2. A user can "eat" another player. The above code will make a player move twice in multiplayer, so I just took it away to stop that false input.
   - **NOTE:** even if the player eats the other player, the player that is eaten can simply move and will reappear. 
3. Telnet and netcat are unencrypted forms of communication between two computers. Meaning it can be sniffed and viewed in play text. In theory someone can hack the game. IMO Im not worried about that. 