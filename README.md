 AudioStreamBox
===============
Listen to the same songs as your friends by syncing audio playback. 

## Dependencies
FFMPEG

Youtube-dl

## How does it work
The clients can request the server to download specific youtube songs. The server converts this to mp3 and distributes this between all connected clients.
The clients request periodically wat song is being played and how many seconds in, and adjusts to that if needed.

## This is a work in progress
