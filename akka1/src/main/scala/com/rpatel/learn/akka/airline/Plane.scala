package com.rpatel.learn.akka.airline

import akka.actor.{Props, ActorLogging, Actor}
import com.rpatel.learn.akka.airline


/**
 * Created by rpatel on 4/4/14.
 */
object Plane {
  case object GiveMeControl
}

class Plane extends Actor with ActorLogging{
  import Plane._
  import EventSourse._
  val altimetter = context.actorOf(Props[Altimeter], "Altimeter")
  val controls = context.actorOf(Props(new airline.ControlSurfaces(altimetter)), "Controls")

  def receive: Receive = {
    case Altimeter.AltimeterUpdate(altitude) =>
      log.info(s"Altitude is now: $altitude")
    case GiveMeControl =>
      log.info("Plane giving up control.")
      sender ! controls
  }

  override def preStart = {
    altimetter ! RegisterListner(self)
  }
}