package org.nirmalya.experiment.test

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._
import Matchers._
import com.redis.RedisClient
import org.json4s.native.Serialization.{read, write}
import com.OneHuddle.GamePlaySessionService.GameSessionStateHolderActor
import com.OneHuddle.GamePlaySessionService.GameSessionHandlingServiceProtocol.HuddleGame.{GameSessionHasStartedState, GameSessionIsContinuingState, GameSessionIsPausedState, GameSessionYetToStartState}
import com.OneHuddle.GamePlaySessionService.GameSessionHandlingServiceProtocol._

import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

import scala.concurrent.duration._
import akka.testkit._
import com.typesafe.config.ConfigFactory
import org.nirmalya.experiment.test.common.StopSystemAfterAll

import scala.language.postfixOps


/**
  * Created by nirmalya on 11/6/17.
  */
class GameSessionRedisDataIntegrityTest extends TestKit(ActorSystem("HuddleGame-system"))
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll
  with ImplicitSender
  with StopSystemAfterAll {

  val config = ConfigFactory.load()

  val (redisHost, redisPort) = (

    config.getConfig("GameSession.redisEndPoint").getString("host"),
    config.getConfig("GameSession.redisEndPoint").getInt("port")
  )

  val maxGameSessionLifetime = Duration(
    config.getConfig("GameSession.maxGameSessionLifetime").getInt("duration"),
    TimeUnit.SECONDS
  )


  val redisUpdateIndicatorAwaitingTime = Duration(
    config.getConfig("GameSession.maxGameSessionLifetime").getInt("duration"),
    TimeUnit.SECONDS
  )


  val redisClient = new RedisClient(redisHost, redisPort)

  val keyPrefix = "HuddleGame-Test-RedisData-"

  val gameStartsAt = System.currentTimeMillis()

  val questionaAndAnswers = IndexedSeq(
    QuestionAnswerTuple(1,1,true,10,5),
    QuestionAnswerTuple(2,2,true,10,5),
    QuestionAnswerTuple(3,3,false,0,5),
    QuestionAnswerTuple(4,4,true,10,5)
  )

  override def beforeAll = super.beforeAll


  "A Huddle GamePlay Session" must {

    "store (Created,Initiated) tuples, in that order, in a history of a game session"  in {

      val actorName =  "RecorderActorForTest-1"

      val probe1 = TestProbe()


      val gameSession = GameSession("CW","QA","G01","P01","Tic-Tac-Toe","UUID-1",playedInTimezone = "Asia/Calcutta")


      val gamePlayRecorderActor = system.actorOf(
        GameSessionStateHolderActor(
          true,
          gameSession,
          redisHost,
          redisPort,
          maxGameSessionLifetime
        ),(actorName + "." + gameSession.gameSessionKey))

      gamePlayRecorderActor ! HuddleGame.EvInitiated(gameStartsAt)

      expectMsg("Initiated")

      gamePlayRecorderActor ! HuddleGame.EvGamePlayRecordSoFarRequired

      expectMsgPF(2 second) {

        case m: String =>
          val sessionHistoryAsJSON = m
          val completeHistory = read[CompleteGamePlaySessionHistory](sessionHistoryAsJSON)
          completeHistory.elems.length should be (2)
          completeHistory.elems.toIndexedSeq(0) shouldBe a [GameCreatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(1) shouldBe a [GameInitiatedTupleInREDIS]
          val aCreatedTuple = completeHistory.elems.toIndexedSeq(0).asInstanceOf[GameCreatedTupleInREDIS]
          val aStartedTuple = completeHistory.elems.toIndexedSeq(1).asInstanceOf[GameInitiatedTupleInREDIS]
          aCreatedTuple.flag should be ("Game Sentinel")
          aStartedTuple.t shouldEqual (gameStartsAt)

      }

    }

    "store (Created,Initiated,Prepared,Played,Paused,Played,Ended) tuples, in that order, in a history of a game session" in {

      val actorName =  "RecorderActorForTest-3"

      val probe1 = TestProbe()

      val gameSession = GameSession("CW","QA","G01","P01","Tic-Tac-Toe","UUID-2",playedInTimezone = "Asia/Calcutta")

      val gamePlayRecorderActor = system.actorOf(
        GameSessionStateHolderActor(
          true,
          gameSession,
          redisHost,
          redisPort,
          maxGameSessionLifetime
        ),(actorName + "." + gameSession.gameSessionKey))

      gamePlayRecorderActor ! HuddleGame.EvInitiated(gameStartsAt)
      expectMsg(("Initiated"))

      gamePlayRecorderActor ! HuddleGame.EvQuizIsFinalized(gameStartsAt+1,"Some metadata")
      expectMsg(("Prepared"))

      gamePlayRecorderActor ! HuddleGame.EvQuestionAnswered(gameStartsAt+2,questionaAndAnswers(0))
      expectMsg(("QuestionAnswered"))


      gamePlayRecorderActor ! HuddleGame.EvPaused(gameStartsAt+3)
      expectMsg(("Paused"))


      gamePlayRecorderActor ! HuddleGame.EvQuestionAnswered(gameStartsAt+4,questionaAndAnswers(1))
      expectMsg(("QuestionAnswered"))

      gamePlayRecorderActor ! HuddleGame.EvEndedByPlayer(
                                           gameStartsAt+5,
                                           5)

      expectMsgPF(2 second) {

        case m: String =>

          m should be ("Ended")
      }

      val history = redisClient.hget(gameSession, "SessionHistory")

      history match {
        case Some (v) =>
          val completeHistory = read[CompleteGamePlaySessionHistory](v)
          completeHistory.elems.length should be (7)
          completeHistory.elems.toIndexedSeq(0) shouldBe a [GameCreatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(1) shouldBe a [GameInitiatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(2) shouldBe a [GamePreparedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(3) shouldBe a [GamePlayedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(4) shouldBe a [GamePausedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(5) shouldBe a [GamePlayedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(6) shouldBe a [GameEndedTupleInREDIS]

          val aCreatedTuple = completeHistory.elems.toIndexedSeq(0).asInstanceOf[GameCreatedTupleInREDIS]
          aCreatedTuple.flag should be ("Game Sentinel")

          val aStartedTuple = completeHistory.elems.toIndexedSeq(1).asInstanceOf[GameInitiatedTupleInREDIS]
          aStartedTuple.t shouldEqual (gameStartsAt)

          val aPreparedTuple1 = completeHistory.elems.toIndexedSeq(2).asInstanceOf[GamePreparedTupleInREDIS]
          aPreparedTuple1.questionMetadata shouldEqual "Some metadata"

          val aPlayTuple1 = completeHistory.elems.toIndexedSeq(3).asInstanceOf[GamePlayedTupleInREDIS]
          aPlayTuple1.questionAnswer shouldEqual (questionaAndAnswers(0))

          val aPlayPausedTuple = completeHistory.elems.toIndexedSeq(4).asInstanceOf[GamePausedTupleInREDIS]
          aPlayPausedTuple.t shouldEqual (gameStartsAt+3)

          val aPlayTuple2 = completeHistory.elems.toIndexedSeq(5).asInstanceOf[GamePlayedTupleInREDIS]
          aPlayTuple2.questionAnswer shouldEqual (questionaAndAnswers(1))

          val anEndedTuple = completeHistory.elems.toIndexedSeq(6).asInstanceOf[GameEndedTupleInREDIS]
          anEndedTuple shouldEqual (GameEndedTupleInREDIS(gameStartsAt+5, GameSessionEndedByPlayer.toString, 5 ))

      }

    }

    "ignore successive Pauses, if that occurs during a game session" in {

      val actorName =  "RecorderActorForTest-4"

      val probe1 = TestProbe()

      val gameSession = GameSession("CW","QA","G01","P01","Tic-Tac-Toe","UUID-4",playedInTimezone = "Asia/Calcutta")

      val gamePlayRecorderActor = system.actorOf(
        GameSessionStateHolderActor(
          true,
          gameSession,
          redisHost,
          redisPort,
          maxGameSessionLifetime
        ),(actorName + "." + gameSession.gameSessionKey))
      gamePlayRecorderActor ! HuddleGame.EvInitiated(gameStartsAt)
      expectMsg(("Initiated"))

      gamePlayRecorderActor ! HuddleGame.EvQuizIsFinalized(gameStartsAt+1,List(1,2,3,4).mkString("|"))
      expectMsg(("Prepared"))

      gamePlayRecorderActor ! HuddleGame.EvQuestionAnswered(gameStartsAt+2,questionaAndAnswers(0))
      expectMsg(("QuestionAnswered"))

      gamePlayRecorderActor ! HuddleGame.EvPaused(gameStartsAt+3)
      expectMsg(("Paused"))

      // Successive Pause, will be ignored by the GameSession's FSM
      gamePlayRecorderActor ! HuddleGame.EvPaused(gameStartsAt+4)
      expectNoMsg(2 second)

      gamePlayRecorderActor ! HuddleGame.EvEndedByPlayer(gameStartsAt+5, 2)

      expectMsgPF(2 second) {

        case m: String =>
          m should be ("Ended")
      }

      val history = redisClient.hget(gameSession, "SessionHistory")

      history match {
        case Some (v) =>
          val completeHistory = read[CompleteGamePlaySessionHistory](v)
          completeHistory.elems.length should be (6)
          completeHistory.elems.toIndexedSeq(0) shouldBe a [GameCreatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(1) shouldBe a [GameInitiatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(2) shouldBe a [GamePreparedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(3) shouldBe a [GamePlayedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(4) shouldBe a [GamePausedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(5) shouldBe a [GameEndedTupleInREDIS]

          val aCreatedTuple = completeHistory.elems.toIndexedSeq(0).asInstanceOf[GameCreatedTupleInREDIS]
          aCreatedTuple.flag should be ("Game Sentinel")

          val aStartedTuple = completeHistory.elems.toIndexedSeq(1).asInstanceOf[GameInitiatedTupleInREDIS]
          aStartedTuple.t shouldEqual (gameStartsAt)

          val aInitiatedTuple = completeHistory.elems.toIndexedSeq(2).asInstanceOf[GamePreparedTupleInREDIS]
          aInitiatedTuple.t shouldEqual (gameStartsAt + 1)

          val aPlayTuple1 = completeHistory.elems.toIndexedSeq(3).asInstanceOf[GamePlayedTupleInREDIS]
          aPlayTuple1.questionAnswer shouldEqual (questionaAndAnswers(0))

          val aPlayPausedTuple = completeHistory.elems.toIndexedSeq(4).asInstanceOf[GamePausedTupleInREDIS]
          aPlayPausedTuple.t shouldEqual (gameStartsAt+3)

          val anEndedTuple = completeHistory.elems.toIndexedSeq(5).asInstanceOf[GameEndedTupleInREDIS]
          anEndedTuple shouldEqual (GameEndedTupleInREDIS(gameStartsAt+5, GameSessionEndedByPlayer.toString,2))

        case None => 0 shouldEqual (1) // Here, we want to know if there is a failure!

      }
    }

    "time out and end the game forcefully, after remaining at Paused state for more than a specified duration" in {

      val actorName =  "RecorderActorForTest-5"

      val probe1 = TestProbe()

      val gameSession = GameSession("CW","QA","G01","P01","Tic-Tac-Toe","UUID-5",playedInTimezone = "Asia/Calcutta")

      val gamePlayRecorderActor = system.actorOf(
        GameSessionStateHolderActor(
          true,
          gameSession,
          redisHost,
          redisPort,
          FiniteDuration(6,TimeUnit.SECONDS) // Overriding the maxGameTimeOut value here, because we want the testcase to finish fast
        ),(actorName + "." + gameSession.gameSessionKey))


      gamePlayRecorderActor ! HuddleGame.EvInitiated(gameStartsAt)
      expectMsg(("Initiated"))

      gamePlayRecorderActor ! HuddleGame.EvQuizIsFinalized(gameStartsAt+1,List(1,2,3,4).mkString("|"))
      expectMsg(("Prepared"))


      gamePlayRecorderActor ! HuddleGame.EvQuestionAnswered(gameStartsAt+2,questionaAndAnswers(0))
      expectMsg(("QuestionAnswered"))

      gamePlayRecorderActor ! HuddleGame.EvPaused(gameStartsAt+3)
      expectMsg(("Paused"))

      awaitCond(
        {
          // 'SessionHistory' below, is hardcoded as the 'V', in (K,V) as stored in REDIS hash.
          val history = redisClient.hget(gameSession, "SessionHistory")

          history match {
            case Some(v) =>
              val completeHistory = read[CompleteGamePlaySessionHistory](v)
              completeHistory.elems.length == 6 // Created+Initiated+Prepared+Played+Paused+Ended
            case None =>
              false
          }
        },
        redisUpdateIndicatorAwaitingTime,
        10 seconds, // We are waiting for 10 seconds, because maxGameTimeOut has been initialized as 6 Seconds, in this case
        "REDIS"
      )

      val history = redisClient.hget(gameSession, "SessionHistory")

      history match {
        case Some(v) =>
          val completeHistory = read[CompleteGamePlaySessionHistory](v)
          completeHistory.elems.length should be(6)
          completeHistory.elems.toIndexedSeq(0) shouldBe a[GameCreatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(1) shouldBe a[GameInitiatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(2) shouldBe a[GamePreparedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(3) shouldBe a[GamePlayedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(4) shouldBe a[GamePausedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(5) shouldBe a[GameEndedTupleInREDIS]

          val aCreatedTuple = completeHistory.elems.toIndexedSeq(0).asInstanceOf[GameCreatedTupleInREDIS]
          aCreatedTuple.flag should be("Game Sentinel")

          val aStartedTuple = completeHistory.elems.toIndexedSeq(1).asInstanceOf[GameInitiatedTupleInREDIS]
          aStartedTuple.t shouldEqual (gameStartsAt)

          val aInitiatedTuple = completeHistory.elems.toIndexedSeq(2).asInstanceOf[GamePreparedTupleInREDIS]
          aInitiatedTuple.t shouldEqual (gameStartsAt + 1)

          val aPlayTuple1 = completeHistory.elems.toIndexedSeq(3).asInstanceOf[GamePlayedTupleInREDIS]
          aPlayTuple1.questionAnswer shouldEqual (questionaAndAnswers(0))

          val aPlayPausedTuple = completeHistory.elems.toIndexedSeq(4).asInstanceOf[GamePausedTupleInREDIS]
          aPlayPausedTuple.t shouldEqual (gameStartsAt + 3)

          val anEndedTuple = completeHistory.elems.toIndexedSeq(5).asInstanceOf[GameEndedTupleInREDIS]
          anEndedTuple.gameEndingReason should be(GameSessionEndedByTimeOut.toString)

        case None => 0 shouldEqual (1) // Here, we want to know if there is a failure!

      }

    }

    "end the game, if the Manager wants it to" in {

      val actorName =  "RecorderActorForTest-6"

      val probe1 = TestProbe()

      val gameSession = GameSession("CW","QA","G01","P01","Tic-Tac-Toe","UUID-5",playedInTimezone = "Asia/Calcutta")

      val gamePlayRecorderActor = system.actorOf(
        GameSessionStateHolderActor(
          true,
          gameSession,
          redisHost,
          redisPort,
          FiniteDuration(6,TimeUnit.SECONDS) // Overriding the maxGameTimeOut value here, because we want the testcase to finish fast
        ),(actorName + "." + gameSession.gameSessionKey))


      gamePlayRecorderActor ! HuddleGame.EvInitiated(gameStartsAt)
      expectMsg(("Initiated"))

      gamePlayRecorderActor ! HuddleGame.EvQuizIsFinalized(gameStartsAt+1,List(1,2,3,4).mkString("|"))
      expectMsg(("Prepared"))


      gamePlayRecorderActor ! HuddleGame.EvQuestionAnswered(gameStartsAt+2,questionaAndAnswers(0))
      expectMsg(("QuestionAnswered"))

      gamePlayRecorderActor ! HuddleGame.EvPaused(gameStartsAt+3)
      expectMsg(("Paused"))

      gamePlayRecorderActor ! HuddleGame.EvForceEndedByManager(gameStartsAt+4,"Vikas")
      expectMsg(("Ended"))

      awaitCond(
        {
          // 'SessionHistory' below, is hardcoded as the 'V', in (K,V) as stored in REDIS hash.
          val history = redisClient.hget(gameSession, "SessionHistory")

          history match {
            case Some(v) =>
              val completeHistory = read[CompleteGamePlaySessionHistory](v)
              completeHistory.elems.length == 6 // Created+Initiated+Prepared+Played+Paused+Ended
            case None =>
              false
          }
        },
        redisUpdateIndicatorAwaitingTime,
        10 seconds, // We are waiting for 10 seconds, because maxGameTimeOut has been initialized as 6 Seconds, in this case
        "REDIS"
      )

      val history = redisClient.hget(gameSession, "SessionHistory")

      history match {
        case Some(v) =>
          val completeHistory = read[CompleteGamePlaySessionHistory](v)
          completeHistory.elems.length should be(6)
          completeHistory.elems.toIndexedSeq(0) shouldBe a[GameCreatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(1) shouldBe a[GameInitiatedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(2) shouldBe a[GamePreparedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(3) shouldBe a[GamePlayedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(4) shouldBe a[GamePausedTupleInREDIS]
          completeHistory.elems.toIndexedSeq(5) shouldBe a[GameEndedTupleInREDIS]

          val aCreatedTuple = completeHistory.elems.toIndexedSeq(0).asInstanceOf[GameCreatedTupleInREDIS]
          aCreatedTuple.flag should be("Game Sentinel")

          val aStartedTuple = completeHistory.elems.toIndexedSeq(1).asInstanceOf[GameInitiatedTupleInREDIS]
          aStartedTuple.t shouldEqual (gameStartsAt)

          val aInitiatedTuple = completeHistory.elems.toIndexedSeq(2).asInstanceOf[GamePreparedTupleInREDIS]
          aInitiatedTuple.t shouldEqual (gameStartsAt + 1)

          val aPlayTuple1 = completeHistory.elems.toIndexedSeq(3).asInstanceOf[GamePlayedTupleInREDIS]
          aPlayTuple1.questionAnswer shouldEqual (questionaAndAnswers(0))

          val aPlayPausedTuple = completeHistory.elems.toIndexedSeq(4).asInstanceOf[GamePausedTupleInREDIS]
          aPlayPausedTuple.t shouldEqual (gameStartsAt + 3)

          val anEndedTuple = completeHistory.elems.toIndexedSeq(5).asInstanceOf[GameEndedTupleInREDIS]
          anEndedTuple.gameEndingReason should be(GameSessionEndedByManager.toString)

        case None => 0 shouldEqual (1) // Here, we want to know if there is a failure!

      }

    }

  }
}
