package controllers

import akka.actor.Actor
import akka.event.Logging
import com.newrelic.api.agent.{NewRelic, Trace}

class MyActor extends Actor {
  val log = Logging(context.system, this)

  @Trace(metricName = "receive", dispatcher = true)
  def receive = {
    case "test" =>
      Thread.sleep(4000)
      println("received test")
      println(s"Trace id on actor is ${NewRelic.getAgent.getTraceMetadata.getTraceId}")
      SomeInnerClass().
        doStuff()
      sender() ! "ok"
    case _      => log.info("received unknown message")
  }
}
