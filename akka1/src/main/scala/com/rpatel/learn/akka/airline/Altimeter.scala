package com.rpatel.learn.akka.airline

import akka.actor.{ActorLogging, Actor}
import scala.concurrent.duration._
import com.rpatel.learn.akka.airline.Altimeter.RateChange
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by rpatel on 4/4/14.
 */
class Altimeter extends Actor with ActorLogging with EventSourse {

  var ceiling:Int = 43000
  val maxRateToClimb:Int = 5000
  var rateOfClimb:Float = 0
  var altitude:Double = 0
  var lastTick:Long = System.currentTimeMillis()
  var ticker = context.system.scheduler.schedule(200.millis, 200.millis, self, Tick)
  case object Tick

  def receive: Receive = eventSourceReceive orElse altimeterReceive

  def altimeterReceive: Receive = {
    case RateChange(amount:Float) =>
      rateOfClimb = amount.min(1.0f).max(-1.0f) * maxRateToClimb
      log.info(s"Altimeter changed rate of climb to $rateOfClimb.")
    case Tick =>
      val tick = System.currentTimeMillis
      altitude = altitude + ((tick-lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
      sendEvent(Altimeter.AltimeterUpdate(altitude))
  }
  override def postStop():Unit = ticker.cancel()

}

object Altimeter{
  case class RateChange(amount: Float)
  case class AltimeterUpdate(altitude : Double)
}
