![BetakeyBanner](https://github.com/user-attachments/assets/05b22568-1735-45f9-95f5-46c540be3753)

## Description
BetaKey is a simple Minecraft plugin to control beta access to your server. You can create your own beta keys and share them with your community. They can then get time-limited access to the server.

## Setup
1. create the spawn point where the players who do not have a key should spawn. You can do this with the command `/betakey setSpawn`.
2. create the spawn point where the players who have redeemed a key should spawn. You can do this with the command `/betakey setLobby`.
3. generate a key with `/betakey generate <count> <days>`. Replace days with the number of days you want the key to last
4. redeem a key with the command `/betakey register <key>`.
--> You can see and edit all your keys and their associated players in `config.yml`.
## Soft-depend
- Luckpems (if you want to use the group features)

## Commands

- /betakey generate <count> <days> --> generate Betakeys
- /betakey register <key> --> register Betakeys
- /betakey clear --> clear old keys from config
- /betakey setSpawn/setLobby --> set Spawn/Lobby
- /betakey license bzw. /license --> show your current license
- /betakey reload --> reload the config

## Permissions
```bash
betakey.generate
betakey.register
betakey.licence
betakey.spawn
```

## Config.yml

```bash

beta: true # disable/enable beta mode
language: en # language (currently en/de)
BetaSpawn: 
  world: world
  x: -74.55800966196762
  y: 88.0
  z: 51.194411419228885
  yaw: -5.591919
  pitch: 10.585971

LobbySpawn:
  world: world
  x: -99.532456099539
  y: 80.0
  z: 11.6024911636488
  yaw: -2.2509155
  pitch: 2.0577488

keys:
  player:
    f7813b5c-b7d9-4cb0-839e-214b5f4214c3: # Change the value behind the uuid to delete/edit the key of a specific player
  open:
    4Y9O-TQVB-NA0K: '' # See the keys a user can still redeem
  time:
    3OL3-S73P-VTF8: 15.11.2024 # Change the date a key will be discontinued
    ZHPU-KO19-533N: 15.11.2024
    4Y9O-TQVB-NA0K: 15.11.2024
# to create your own keys, enter your key under “time” and create an expiry date. Then you have to add the key to open and mark it with a ''
# Example:
# open:
#   example-key: ''
# time:
#   example-key: 22.01.2029
luckperms:
  player_group: default   # Edit this to the group you want the player to join after a registration

```
