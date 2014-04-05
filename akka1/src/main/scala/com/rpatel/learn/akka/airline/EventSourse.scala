package com.rpatel.learn.akka.airline

import akka.actor.{Actor, ActorRef}

/**
 * Created by rpatel on 4/4/14.
 */
object EventSourse {
  case class RegisterListner(listner:ActorRef)
  case class UnRegisterListner(listner:ActorRef)
}

trait EventSourse { this: Actor =>
  import EventSourse._
  var listners = Vector.empty[ActorRef]

  def sendEvent[T] (event: T):Unit = listners.foreach { _ ! event}

  def eventSourceReceive: Receive = {
    case RegisterListner(listner) => listners = listners :+ listner
    case UnRegisterListner(listner) => listners = listners filter { _ != listner }
  }
}
