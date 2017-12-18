/*
 * This file is generated by jOOQ.
*/
package com.OneHuddle.GamePlaySessionService.jOOQ.generated


import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Datetimexperiment
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Gamesessionrecords
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Liveboardsnapshots
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerdetails
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerperformance

import java.util.ArrayList
import java.util.Arrays
import java.util.List

import javax.annotation.Generated

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl

import scala.Array


object Onehuddle {

  /**
   * The reference instance of <code>OneHuddle</code>
   */
  val ONEHUDDLE = new Onehuddle
}

/**
 * This class is generated by jOOQ.
 */
@Generated(
  value = Array(
    "http://www.jooq.org",
    "jOOQ version:3.10.0"
  ),
  comments = "This class is generated by jOOQ"
)
class Onehuddle extends SchemaImpl("OneHuddle", DefaultCatalog.DEFAULT_CATALOG) {

  override def getCatalog : Catalog = DefaultCatalog.DEFAULT_CATALOG

  override def getTables : List[Table[_]] = {
    val result = new ArrayList[Table[_]]
    result.addAll(getTables0)
    result
  }

  private def getTables0(): List[Table[_]] = {
    return Arrays.asList[Table[_]](
      Datetimexperiment.DATETIMEXPERIMENT,
      Gamesessionrecords.GAMESESSIONRECORDS,
      Liveboardsnapshots.LIVEBOARDSNAPSHOTS,
      Playerdetails.PLAYERDETAILS,
      Playerperformance.PLAYERPERFORMANCE)
  }
}
