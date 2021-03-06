package org.nirmalya.experiment.test

import akka.actor.ActorSystem
import akka.testkit.{EventFilter, TestKit}
import com.typesafe.config.ConfigFactory
import com.OneHuddle.GamePlaySessionService.GameSessionCompletionEmitterActor
import com.OneHuddle.GamePlaySessionService.GameSessionHandlingServiceProtocol.EmittedWhenGameSessionIsFinished

import collection.JavaConversions._
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created by nirmalya on 3/7/17.
  */
class GameSessionCompletionEmitterActorTest
  extends TestKit(ActorSystem(
    "GameSessionFinishEmission",
    ConfigFactory.parseString(
      """
        |akka.loggers = ["akka.testkit.TestEventListener"]
        |""".stripMargin)))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll{

  override def beforeAll = super.beforeAll

  val config = ConfigFactory.load()

  "A GameSessionCompletionEmitterActor" must {
      "Verify that record emitted (when a GameSession is finished) to a valid HTTP endpoint,  is trapped expectedly" in {

        val gameSessionCompletionSubscriberEndpoints =
          config.getConfig("GameSession.externalServices").getStringList("completionSubscribers").toList

        val gameSessionCompletionEmitter =
          system
            .actorOf(
              GameSessionCompletionEmitterActor(gameSessionCompletionSubscriberEndpoints),"EmitterOnFinishingGameSession-1")


        EventFilter.info(pattern = "Emitted", occurrences = 1) intercept {
          gameSessionCompletionEmitter ! EmittedWhenGameSessionIsFinished("GameSession-Finished")
        }
      }

    "Verify that record emitted (when a GameSession is finished) to an invalid HTTP endpoint,  is trapped expectedly" in {

      val gameSessionCompletionSubscriberEndpoints = List("http://localhost:9991/put")

      val gameSessionCompletionEmitter =
        system
          .actorOf(
            GameSessionCompletionEmitterActor(gameSessionCompletionSubscriberEndpoints),"EmitterOnFinishingGameSession-2")


      EventFilter.info(pattern = "Failed:", occurrences = 1) intercept {
        gameSessionCompletionEmitter ! EmittedWhenGameSessionIsFinished("GameSession-Finished")
      }
    }
  }

}
