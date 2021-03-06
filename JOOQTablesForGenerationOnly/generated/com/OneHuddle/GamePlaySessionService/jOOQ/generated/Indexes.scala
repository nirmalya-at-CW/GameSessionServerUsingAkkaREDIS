/*
 * This file is generated by jOOQ.
*/
package com.OneHuddle.GamePlaySessionService.jOOQ.generated


import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Datetimexperiment
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Gamesessionrecords
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerdetails
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerperformance

import javax.annotation.Generated

import org.jooq.Index
import org.jooq.OrderField
import org.jooq.impl.AbstractKeys

import scala.Array


/**
 * A class modelling indexes of tables of the <code>OneHuddle</code> schema.
 */
@Generated(
  value = Array(
    "http://www.jooq.org",
    "jOOQ version:3.10.0"
  ),
  comments = "This class is generated by jOOQ"
)
object Indexes {

  // -------------------------------------------------------------------------
  // INDEX definitions
  // -------------------------------------------------------------------------

  val DATETIMEXPERIMENT_PRIMARY = Indexes0.DATETIMEXPERIMENT_PRIMARY
  val GAMESESSIONRECORDS_PRIMARY = Indexes0.GAMESESSIONRECORDS_PRIMARY
  val PLAYERDETAILS_PRIMARY = Indexes0.PLAYERDETAILS_PRIMARY
  val PLAYERPERFORMANCE_PRIMARY = Indexes0.PLAYERPERFORMANCE_PRIMARY

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private object Indexes0 extends AbstractKeys {
    val DATETIMEXPERIMENT_PRIMARY : Index = AbstractKeys.createIndex("PRIMARY", Datetimexperiment.DATETIMEXPERIMENT, Array[OrderField [_] ](Datetimexperiment.DATETIMEXPERIMENT.JUSTID), true)
    val GAMESESSIONRECORDS_PRIMARY : Index = AbstractKeys.createIndex("PRIMARY", Gamesessionrecords.GAMESESSIONRECORDS, Array[OrderField [_] ](Gamesessionrecords.GAMESESSIONRECORDS.COMPANYID, Gamesessionrecords.GAMESESSIONRECORDS.BELONGSTODEPARTMENT, Gamesessionrecords.GAMESESSIONRECORDS.PLAYERID, Gamesessionrecords.GAMESESSIONRECORDS.GAMEID, Gamesessionrecords.GAMESESSIONRECORDS.GAMESESSIONUUID), true)
    val PLAYERDETAILS_PRIMARY : Index = AbstractKeys.createIndex("PRIMARY", Playerdetails.PLAYERDETAILS, Array[OrderField [_] ](Playerdetails.PLAYERDETAILS.COMPANYID, Playerdetails.PLAYERDETAILS.BELONGSTODEPARTMENT, Playerdetails.PLAYERDETAILS.PLAYERID), true)
    val PLAYERPERFORMANCE_PRIMARY : Index = AbstractKeys.createIndex("PRIMARY", Playerperformance.PLAYERPERFORMANCE, Array[OrderField [_] ](Playerperformance.PLAYERPERFORMANCE.RECORDID, Playerperformance.PLAYERPERFORMANCE.COMPANYID, Playerperformance.PLAYERPERFORMANCE.BELONGSTODEPARTMENT, Playerperformance.PLAYERPERFORMANCE.PLAYERID, Playerperformance.PLAYERPERFORMANCE.GAMEID, Playerperformance.PLAYERPERFORMANCE.GAMETYPE, Playerperformance.PLAYERPERFORMANCE.LASTPLAYEDON, Playerperformance.PLAYERPERFORMANCE.TIMEZONEAPPLICABLE), true)
  }
}
