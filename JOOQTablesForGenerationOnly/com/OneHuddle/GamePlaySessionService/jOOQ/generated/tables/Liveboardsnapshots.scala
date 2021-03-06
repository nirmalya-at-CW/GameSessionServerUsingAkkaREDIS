/*
 * This file is generated by jOOQ.
*/
package com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables


import com.OneHuddle.GamePlaySessionService.jOOQ.generated.Indexes
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.Keys
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.Onehuddle
import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records.LiveboardsnapshotsRecord

import java.lang.Class
import java.lang.Integer
import java.lang.String
import java.sql.Timestamp
import java.util.Arrays
import java.util.List

import javax.annotation.Generated

import org.jooq.Field
import org.jooq.Identity
import org.jooq.Index
import org.jooq.Name
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.TableImpl

import scala.Array


object Liveboardsnapshots {

  /**
   * The reference instance of <code>OneHuddle.LiveBoardSnapshots</code>
   */
  val LIVEBOARDSNAPSHOTS = new Liveboardsnapshots
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
class Liveboardsnapshots(alias : Name, aliased : Table[LiveboardsnapshotsRecord], parameters : Array[ Field[_] ]) extends TableImpl[LiveboardsnapshotsRecord](alias, Onehuddle.ONEHUDDLE, aliased, parameters, "") {

  /**
   * The class holding records for this type
   */
  override def getRecordType : Class[LiveboardsnapshotsRecord] = {
    classOf[LiveboardsnapshotsRecord]
  }

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.recordID</code>.
   */
  val RECORDID : TableField[LiveboardsnapshotsRecord, Integer] = createField("recordID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.snapshotTakenAt</code>.
   */
  val SNAPSHOTTAKENAT : TableField[LiveboardsnapshotsRecord, Timestamp] = createField("snapshotTakenAt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.timezoneApplicable</code>.
   */
  val TIMEZONEAPPLICABLE : TableField[LiveboardsnapshotsRecord, String] = createField("timezoneApplicable", org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.companyID</code>.
   */
  val COMPANYID : TableField[LiveboardsnapshotsRecord, String] = createField("companyID", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.belongsToDepartment</code>.
   */
  val BELONGSTODEPARTMENT : TableField[LiveboardsnapshotsRecord, String] = createField("belongsToDepartment", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.playerID</code>.
   */
  val PLAYERID : TableField[LiveboardsnapshotsRecord, String] = createField("playerID", org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.gameID</code>.
   */
  val GAMEID : TableField[LiveboardsnapshotsRecord, String] = createField("gameID", org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.gameType</code>.
   */
  val GAMETYPE : TableField[LiveboardsnapshotsRecord, String] = createField("gameType", org.jooq.impl.SQLDataType.VARCHAR(8).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.groupID</code>.
   */
  val GROUPID : TableField[LiveboardsnapshotsRecord, String] = createField("groupID", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.field("NOTSET", org.jooq.impl.SQLDataType.VARCHAR)), "")

  /**
   * The column <code>OneHuddle.LiveBoardSnapshots.rankComputed</code>.
   */
  val RANKCOMPUTED : TableField[LiveboardsnapshotsRecord, Integer] = createField("rankComputed", org.jooq.impl.SQLDataType.INTEGER, "")

  /**
   * Create a <code>OneHuddle.LiveBoardSnapshots</code> table reference
   */
  def this() = {
    this(DSL.name("LiveBoardSnapshots"), null, null)
  }

  /**
   * Create an aliased <code>OneHuddle.LiveBoardSnapshots</code> table reference
   */
  def this(alias : String) = {
    this(DSL.name(alias), com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Liveboardsnapshots.LIVEBOARDSNAPSHOTS, null)
  }

  /**
   * Create an aliased <code>OneHuddle.LiveBoardSnapshots</code> table reference
   */
  def this(alias : Name) = {
    this(alias, com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Liveboardsnapshots.LIVEBOARDSNAPSHOTS, null)
  }

  private def this(alias : Name, aliased : Table[LiveboardsnapshotsRecord]) = {
    this(alias, aliased, null)
  }

  override def getSchema : Schema = Onehuddle.ONEHUDDLE

  override def getIndexes : List[ Index ] = {
    return Arrays.asList[ Index ](Indexes.LIVEBOARDSNAPSHOTS_PRIMARY)
  }

  override def getIdentity : Identity[LiveboardsnapshotsRecord, Integer] = {
    Keys.IDENTITY_LIVEBOARDSNAPSHOTS
  }

  override def getPrimaryKey : UniqueKey[LiveboardsnapshotsRecord] = {
    Keys.KEY_LIVEBOARDSNAPSHOTS_PRIMARY
  }

  override def getKeys : List[ UniqueKey[LiveboardsnapshotsRecord] ] = {
    return Arrays.asList[ UniqueKey[LiveboardsnapshotsRecord] ](Keys.KEY_LIVEBOARDSNAPSHOTS_PRIMARY)
  }

  override def as(alias : String) : Liveboardsnapshots = {
    new Liveboardsnapshots(DSL.name(alias), this)
  }

  override def as(alias : Name) : Liveboardsnapshots = {
    new Liveboardsnapshots(alias, this)
  }

  /**
   * Rename this table
   */
  override def rename(name : String) : Liveboardsnapshots = {
    new Liveboardsnapshots(DSL.name(name), null)
  }

  /**
   * Rename this table
   */
  override def rename(name : Name) : Liveboardsnapshots = {
    new Liveboardsnapshots(name, null)
  }
}
