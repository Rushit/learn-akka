package com.rpatel.learn.akka.airline

/**
 * Created by rpatel on 4/4/14.
 */
import scala.concurrent.duration._
import akka.util.Timeout
import akka.actor.{ActorRef, Props, ActorSystem}
import scala.concurrent.Await
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global

object Aviation  {
  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("PlaneSimulation")
  val plane1= system.actorOf(Props[Plane], "Plane1")
  //val plane2 = system.actorOf(Props[Plane], "Plane2")

  def demo = {
    val controls1 = Await.result((plane1 ? Plane.GiveMeControl).mapTo[ActorRef],5.seconds)
    //val controls2 = Await.result( (plane2 ? Plane.GiveMeControl).mapTo[ActorRef],5.seconds)


    system.scheduler.scheduleOnce(200.millis) {
      controls1 ! ControlSurfaces.StickBack(1f)
      //controls2 ! ControlSurfaces.StickBack(2f)
    }

    system.scheduler.scheduleOnce(1.seconds) {
      controls1 ! ControlSurfaces.StickForward(0f)
      //controls2 ! ControlSurfaces.StickForward(0f)
    }

    system.scheduler.scheduleOnce(3.seconds) {
      controls1 ! ControlSurfaces.StickBack(0.7f)
      //controls2 ! ControlSurfaces.StickBack(0.5f)
    }

    system.scheduler.scheduleOnce(4.seconds) {
      controls1 ! ControlSurfaces.StickBack(0f)
      //controls2 ! ControlSurfaces.StickBack(0f)
    }

    system.scheduler.scheduleOnce(5.seconds) {
      system.shutdown()
    }

    println("done with main")
  }
}