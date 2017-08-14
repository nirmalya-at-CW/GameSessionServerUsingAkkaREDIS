
## Description
In 1Huddle, we are reimplementing the functionality of recording Game Sessions. At this point in time,
it is PoC and is **very much an WIP**.

## The objective is to run this as an _independent_ Service. It:

 *   Is accessible using REST-style APIs.
 *   Only manages the Game Session, not any of the downstream functionality
 *   Expects the caller to obtain UUID from a different source, but 
     uniqueness of Game Sessions is extremely important for its functioning.
 *   Is self-sufficient for storing and retrieving its own data.
 *   Expects another Service to be available, which consumes the GameSession information
     after it is *Complete* (refer to https://codewalla.atlassian.net/wiki/display/1NFL/Game+Session%2C+its+nature+and+our+approach+-+Part+1)

## Implementation outline

This service (named GameSessionRecordingServer) is implemented using Akka-HTTP and Akka-FSM. The language
used is Scala (which is almost always easily interoperable with Java). The software is bundled and deployed as 
a regular Java JAR file.

For every Game Session, an Actor named *GamePlayRecorderActor (org.nirmalya.experiments.GamePlayRecorderActor)* is created automatically. It represents, in 1Huddle's
backend, the game being played in the real-world. This Actor implements a very small Finite-State-Machine (FSM) and 
responds to a handful events, viz., EvStarted, EvPaused etc. It also sets up a timer for each Game Session, which is
fired to indicate a timeout on the part of the Player. 

Another Actor, named *GameSessionSPOCActor* assumes the role of a gate-keeper. It 
keeps track of all the *GamePlayRecorderActors* currently alive and forwards requests (API) 
made from the external world, to the intended recorders.

*GameSessionSPOCActor* is initialized (during construction) with a specialized actor, named
_GameSessionCompletionEmitterActor_. Its job is to call specific (predefined) HTTP endpoints, with the entire
record of a finished GameSession. A good example of such endpoints is that of a **Leaderboard Calculation Service**.
Whenever a Game Session is finished, the _GamePlayRecorderActor_ - which is responsible for that session - passes
the final record to _GameSessionCompletionEmitterActor_. The latter, then contacts the HTTP endpoints mentioned 
earlier. THe HTTP operation used is **PUT**. 

A instance of REDIS in-memory database is assumed to be available. Recorder Actors use this for storing all
session-related transient data.

## How to build and run
* Presence of Java (JDK 1.8+) is a pre-requisite.
* Ensure that JAVA_HOME environment variable is set up properly.
* Ensure that $JAVA_HOME/bin and $JAVA_HOME/lib are added to PATH environment variable.
* Ensure that _sbt_ is installed and is also available through the PATH environment variable.
* Be at the directory where this project has been _pulled_ to.
* Fire: _sbt compile test run_
* If it is being run for the first time, sbt will download a number of dependent JARs. Ensure that you are connected to
 'Net and be patient. The whole process may take time.
* src/main/resources/application.conf contains all the application specific parameters. Amongst other things, it contains
  server endpoints (host,port) and REDIS endpoints (host,port).
* If REDIS cannot be reached, the server emits an explanatory message and refuses to start.  
* At this point, timeout for a game session occurs after 20 seconds. This is hardcoded.

##   Major TODOs
*   One connection per _GamePlayRecorderActor_ may soon hit a wall, 
because each such Actor may remain alive for a long duration. We have
to look for an alternative of 'pooled connections'.
*   We have to implement a mirror actor for every _GamePlayRecorderActor_
to provide availability. How will these two communicate?
*   _GameSessionSPOCActor_ should periodically check if REDIS is accessible.
If it is not, then a warning message must be logged and a message must
be sent to the Admin Actor (TBD).
*   _GameSessionCompletionEmitterActor_ must call the HTTP Endpoints using
JSON. At the moment, it is passing Text.
*   Error codes emitted by GameSessionRecorderActor should be properly folded inside
responses that callers of the Service expects (refer to [confluence page](https://codewalla.atlassian.net/wiki/spaces/1NFL/pages/17727490/GameSessionRecordingService+Requests+and+Responses) ).

##   Example JSON messages for posting to GameSessionHandlingService

Keys are important, values are just examples.

##  Request to start a game (endpoint:  /start)
    {
        "companyID":"1"
        "company":"ABC",
        "manager":"Vikas",
        "playerID":"Nirmalya",
        "gameID":"2",
        "gameName":"1Hudd",
        "gameUUID":"A123"
    }
    
##  Response to start a game
    
    {
       "opSuccess":true,
       "message":{
          "successId":2100,
          "description":"Initiated"
       },
       "contents":{
          "dataCarried":{
             "gameSessionID":"1.ABC.Vikas.Nirmalya.1.1Hudd.A123"
          }
       }
    }
    
    It is perhaps obvious how the **SessionID** is formed. We are simply 'dot-separating' the fields that are supplied with
    '/start' request. Ensuring uniqueness is the **responsibility of the caller**. Even if all the other fields are the same,
    'gameUUID' is guaranteed to be unique. 
##  Request to prepare a game (endpoint:  /prepare)
    {
       "sessionID":"ABC.Vikas.playerID.1Hudd.A123",
       "questionMetadata":"some metadata, to be used by downstream processors"
    }

##  Response to play a game

   {
      "opSuccess":true,
      "message":{
         "successId":2200,
         "description":"Prepared"
      }
   }
    
    
Note: At this point, the GameSession has started, so that can one can play (below).

##  Request to play a game (endpoint:  /play)
    {
       "sessionID":"ABC.Vikas.playerID.1Hudd.A123",
       "questionID":"1",
       "answerID":"2",
       "isCorrect":true,
       "points":200
       "timeSpentToAnswerAtFE": 2
    }

##  Response to play a game

    {
       "opSuccess":true,
       "message":{
          "successId":2200,
          "description":"QuestionAnswered"
       }
    }
    
##  Request to play an audio/video clip during a game (endpoint:  /playClip)
    {
       "sessionID":"ABC.Vikas.playerID.1Hudd.A123",
       "clipName":"kishorekumar.mp3"
    }

##  Response to play an audio/video clip during a game 

    {
       "opSuccess":true,
       "message":{
          "successId":2200,
          "description":"QuestionAnswered"
       }
    }    
    
##  Request to pause a game (endpoint: /pause)
    
    {
           "sessionID":"ABC.Vikas.playerID.1Hudd.A123"
    }
    
##  Response to pause a game

    {
           "opSuccess":true,
           "message":{
              "successId":2200,
              "description":"Paused"
           }
    }
    
##  Request to end a game (endpoint: /end)
     
    {
        "sessionID":"ABC.Vikas.playerID.1Hudd.A123",
        "totalTimeTakenByPlayerAtFE":23
    }
    
## Response to end a game
    
    {
       "opSuccess":true,
       "message":{
          "successId":2200,
          "description":"Ended"
       }
    }

##  Request to end a game by the Manager (endpoint: /endByManager)
     
    {
        "sessionID":"ABC.Vikas.playerID.1Hudd.A123",
        "managerName":"Vikas"
    }
        
    
## Response to end a game by the Manager
    
    {
       "opSuccess":true,
       "message":{
          "successId":2200,
          "description":"Ended"
       }
    }


# _curl_ command examples

### start
curl -v -H "Content-Type: application/json" -X POST -d '{"companyName":"ABC","companyID":1,"manager":"Vikas","playerID":"Nirmalya","gameName":"1Hudd","gameID":1,"gameSessionUUID":"A123"}' http://localhost:9090/start

### prepare
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"1.ABC.Vikas.Nirmalya.1.1Hudd.A123","questionMetadata":"some metadata"}' http://localhost:9090/prepare

### play
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"1.ABC.Vikas.Nirmalya.1.1Hudd.A123","questionID":"1", "answerID":"2","isCorrect":true,"points":200,"timeSpentToAnswerAtFE": 2}' http://localhost:9090/play

### pause
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"1.ABC.Vikas.Nirmalya.1.1Hudd.A123","questionID":"1"}' http://localhost:9090/pause

### playClip
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"1.ABC.Vikas.Nirmalya.1.1Hudd.A123","clipName":"KishoreKumar.mp3"}' http://localhost:9090/playClip 

### end
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"ABC.Vikas.playerID.1Hudd.A123","totalTimeTakenByPlayerAtFE":23}' http://localhost:9090/end

### endByManager
curl -v -H "Content-Type: application/json" -X POST -d '{"sessionID":"ABC.Vikas.playerID.1Hudd.A123","managerName":"Vikas"}' http://localhost:9090/endByManager