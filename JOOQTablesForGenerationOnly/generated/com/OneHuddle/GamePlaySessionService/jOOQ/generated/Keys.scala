/*
 * This file is generated by jOOQ.
*/
package com.OneHuddle.GamePlaySessionService.jOOQ.generated


import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Datetimexperiment
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Gamesessionrecords
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerdetails
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerperformance
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records.DatetimexperimentRecord
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records.GamesessionrecordsRecord
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records.PlayerdetailsRecord
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records.PlayerperformanceRecord

import java.lang.Integer

import javax.annotation.Generated

import org.jooq.Identity
import org.jooq.UniqueKey
import org.jooq.impl.AbstractKeys

import scala.Array


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>OneHuddle</code> schema.
 */
@Generated(
  value = Array(
    "http://www.jooq.org",
    "jOOQ version:3.10.0"
  ),
  comments = "This class is generated by jOOQ"
)
object Keys {

  // -------------------------------------------------------------------------
  // IDENTITY definitions
  // -------------------------------------------------------------------------

  val IDENTITY_PLAYERPERFORMANCE = Identities0.IDENTITY_PLAYERPERFORMANCE

  // -------------------------------------------------------------------------
  // UNIQUE and PRIMARY KEY definitions
  // -------------------------------------------------------------------------

  val KEY_DATETIMEXPERIMENT_PRIMARY = UniqueKeys0.KEY_DATETIMEXPERIMENT_PRIMARY
  val KEY_GAMESESSIONRECORDS_PRIMARY = UniqueKeys0.KEY_GAMESESSIONRECORDS_PRIMARY
  val KEY_PLAYERDETAILS_PRIMARY = UniqueKeys0.KEY_PLAYERDETAILS_PRIMARY
  val KEY_PLAYERPERFORMANCE_PRIMARY = UniqueKeys0.KEY_PLAYERPERFORMANCE_PRIMARY

  // -------------------------------------------------------------------------
  // FOREIGN KEY definitions
  // -------------------------------------------------------------------------


  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private object Identities0 extends AbstractKeys {
    val IDENTITY_PLAYERPERFORMANCE : Identity[PlayerperformanceRecord, Integer] = AbstractKeys.createIdentity(Playerperformance.PLAYERPERFORMANCE, Playerperformance.PLAYERPERFORMANCE.RECORDID)
  }

  private object UniqueKeys0 extends AbstractKeys {
    val KEY_DATETIMEXPERIMENT_PRIMARY : UniqueKey[DatetimexperimentRecord] = AbstractKeys.createUniqueKey(Datetimexperiment.DATETIMEXPERIMENT, "KEY_DateTimExperiment_PRIMARY", Datetimexperiment.DATETIMEXPERIMENT.JUSTID)
    val KEY_GAMESESSIONRECORDS_PRIMARY : UniqueKey[GamesessionrecordsRecord] = AbstractKeys.createUniqueKey(Gamesessionrecords.GAMESESSIONRECORDS, "KEY_GameSessionRecords_PRIMARY", Gamesessionrecords.GAMESESSIONRECORDS.COMPANYID, Gamesessionrecords.GAMESESSIONRECORDS.BELONGSTODEPARTMENT, Gamesessionrecords.GAMESESSIONRECORDS.PLAYERID, Gamesessionrecords.GAMESESSIONRECORDS.GAMEID, Gamesessionrecords.GAMESESSIONRECORDS.GAMESESSIONUUID)
    val KEY_PLAYERDETAILS_PRIMARY : UniqueKey[PlayerdetailsRecord] = AbstractKeys.createUniqueKey(Playerdetails.PLAYERDETAILS, "KEY_PlayerDetails_PRIMARY", Playerdetails.PLAYERDETAILS.COMPANYID, Playerdetails.PLAYERDETAILS.BELONGSTODEPARTMENT, Playerdetails.PLAYERDETAILS.PLAYERID)
    val KEY_PLAYERPERFORMANCE_PRIMARY : UniqueKey[PlayerperformanceRecord] = AbstractKeys.createUniqueKey(Playerperformance.PLAYERPERFORMANCE, "KEY_PlayerPerformance_PRIMARY", Playerperformance.PLAYERPERFORMANCE.RECORDID, Playerperformance.PLAYERPERFORMANCE.COMPANYID, Playerperformance.PLAYERPERFORMANCE.BELONGSTODEPARTMENT, Playerperformance.PLAYERPERFORMANCE.PLAYERID, Playerperformance.PLAYERPERFORMANCE.GAMEID, Playerperformance.PLAYERPERFORMANCE.GAMETYPE, Playerperformance.PLAYERPERFORMANCE.LASTPLAYEDON, Playerperformance.PLAYERPERFORMANCE.TIMEZONEAPPLICABLE)
  }
}
