package controllers

import com.newrelic.api.agent.{NewRelic, Trace}

case class SomeInnerClass() {
  @Trace(metricName = "doStuff")
  def doStuff() = {
    Thread.sleep(2000)
    println(s"trace id on some inner class ${NewRelic.getAgent.getTraceMetadata.getTraceId}")
    println("doing some cool stuff")
  }
}
