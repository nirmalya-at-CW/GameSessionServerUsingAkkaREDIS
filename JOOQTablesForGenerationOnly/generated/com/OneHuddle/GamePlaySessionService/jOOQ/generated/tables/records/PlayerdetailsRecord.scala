/*
 * This file is generated by jOOQ.
*/
package com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.records


import com.OneHuddle.GamePlaySessionService.jOOQ.generated.tables.Playerdetails

import java.lang.String

import javax.annotation.Generated

import org.jooq.Field
import org.jooq.Record3
import org.jooq.Record8
import org.jooq.Row8
import org.jooq.impl.UpdatableRecordImpl

import scala.Array


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
class PlayerdetailsRecord extends UpdatableRecordImpl[PlayerdetailsRecord](Playerdetails.PLAYERDETAILS) with Record8[String, String, String, String, String, String, String, String] {

  /**
   * Setter for <code>OneHuddle.PlayerDetails.companyID</code>.
   */
  def setCompanyid(value : String) : Unit = {
    set(0, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.companyID</code>.
   */
  def getCompanyid : String = {
    val r = get(0)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.companyName</code>.
   */
  def setCompanyname(value : String) : Unit = {
    set(1, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.companyName</code>.
   */
  def getCompanyname : String = {
    val r = get(1)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.belongsToDepartment</code>.
   */
  def setBelongstodepartment(value : String) : Unit = {
    set(2, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.belongsToDepartment</code>.
   */
  def getBelongstodepartment : String = {
    val r = get(2)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.playerID</code>.
   */
  def setPlayerid(value : String) : Unit = {
    set(3, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.playerID</code>.
   */
  def getPlayerid : String = {
    val r = get(3)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.playerName</code>.
   */
  def setPlayername(value : String) : Unit = {
    set(4, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.playerName</code>.
   */
  def getPlayername : String = {
    val r = get(4)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.playerEMailID</code>.
   */
  def setPlayeremailid(value : String) : Unit = {
    set(5, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.playerEMailID</code>.
   */
  def getPlayeremailid : String = {
    val r = get(5)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.applicableTimeZone</code>.
   */
  def setApplicabletimezone(value : String) : Unit = {
    set(6, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.applicableTimeZone</code>.
   */
  def getApplicabletimezone : String = {
    val r = get(6)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>OneHuddle.PlayerDetails.belongsToGroup</code>.
   */
  def setBelongstogroup(value : String) : Unit = {
    set(7, value)
  }

  /**
   * Getter for <code>OneHuddle.PlayerDetails.belongsToGroup</code>.
   */
  def getBelongstogroup : String = {
    val r = get(7)
    if (r == null) null else r.asInstanceOf[String]
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------
  override def key : Record3[String, String, String] = {
    return super.key.asInstanceOf[ Record3[String, String, String] ]
  }

  // -------------------------------------------------------------------------
  // Record8 type implementation
  // -------------------------------------------------------------------------

  override def fieldsRow : Row8[String, String, String, String, String, String, String, String] = {
    super.fieldsRow.asInstanceOf[ Row8[String, String, String, String, String, String, String, String] ]
  }

  override def valuesRow : Row8[String, String, String, String, String, String, String, String] = {
    super.valuesRow.asInstanceOf[ Row8[String, String, String, String, String, String, String, String] ]
  }
  override def field1 : Field[String] = Playerdetails.PLAYERDETAILS.COMPANYID
  override def field2 : Field[String] = Playerdetails.PLAYERDETAILS.COMPANYNAME
  override def field3 : Field[String] = Playerdetails.PLAYERDETAILS.BELONGSTODEPARTMENT
  override def field4 : Field[String] = Playerdetails.PLAYERDETAILS.PLAYERID
  override def field5 : Field[String] = Playerdetails.PLAYERDETAILS.PLAYERNAME
  override def field6 : Field[String] = Playerdetails.PLAYERDETAILS.PLAYEREMAILID
  override def field7 : Field[String] = Playerdetails.PLAYERDETAILS.APPLICABLETIMEZONE
  override def field8 : Field[String] = Playerdetails.PLAYERDETAILS.BELONGSTOGROUP
  override def component1 : String = getCompanyid
  override def component2 : String = getCompanyname
  override def component3 : String = getBelongstodepartment
  override def component4 : String = getPlayerid
  override def component5 : String = getPlayername
  override def component6 : String = getPlayeremailid
  override def component7 : String = getApplicabletimezone
  override def component8 : String = getBelongstogroup
  override def value1 : String = getCompanyid
  override def value2 : String = getCompanyname
  override def value3 : String = getBelongstodepartment
  override def value4 : String = getPlayerid
  override def value5 : String = getPlayername
  override def value6 : String = getPlayeremailid
  override def value7 : String = getApplicabletimezone
  override def value8 : String = getBelongstogroup

  override def value1(value : String) : PlayerdetailsRecord = {
    setCompanyid(value)
    this
  }

  override def value2(value : String) : PlayerdetailsRecord = {
    setCompanyname(value)
    this
  }

  override def value3(value : String) : PlayerdetailsRecord = {
    setBelongstodepartment(value)
    this
  }

  override def value4(value : String) : PlayerdetailsRecord = {
    setPlayerid(value)
    this
  }

  override def value5(value : String) : PlayerdetailsRecord = {
    setPlayername(value)
    this
  }

  override def value6(value : String) : PlayerdetailsRecord = {
    setPlayeremailid(value)
    this
  }

  override def value7(value : String) : PlayerdetailsRecord = {
    setApplicabletimezone(value)
    this
  }

  override def value8(value : String) : PlayerdetailsRecord = {
    setBelongstogroup(value)
    this
  }

  override def values(value1 : String, value2 : String, value3 : String, value4 : String, value5 : String, value6 : String, value7 : String, value8 : String) : PlayerdetailsRecord = {
    this.value1(value1)
    this.value2(value2)
    this.value3(value3)
    this.value4(value4)
    this.value5(value5)
    this.value6(value6)
    this.value7(value7)
    this.value8(value8)
    this
  }

  /**
   * Create a detached, initialised PlayerdetailsRecord
   */
  def this(companyid : String, companyname : String, belongstodepartment : String, playerid : String, playername : String, playeremailid : String, applicabletimezone : String, belongstogroup : String) = {
    this()

    set(0, companyid)
    set(1, companyname)
    set(2, belongstodepartment)
    set(3, playerid)
    set(4, playername)
    set(5, playeremailid)
    set(6, applicabletimezone)
    set(7, belongstogroup)
  }
}