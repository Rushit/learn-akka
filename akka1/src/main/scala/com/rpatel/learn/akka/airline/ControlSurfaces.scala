package com.rpatel.learn.akka.airline

import akka.actor.{ActorRef, Actor}
import com.rpatel.learn.akka.airline.ControlSurfaces.StickBack

/**
 * Created by rpatel on 4/4/14.
 */
class ControlSurfaces(altimerter: ActorRef) extends Actor{
  import Altimeter._
  import ControlSurfaces._
  def receive:Receive = {
    case StickBack(amount) =>
      altimerter ! RateChange(amount)
    case StickForward(amount) =>
      altimerter ! RateChange(-1 * amount)
  }
}

object ControlSurfaces{
  case class StickBack(amout: Float)
  case class StickForward(amount: Float)
}
