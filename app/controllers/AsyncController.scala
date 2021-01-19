package controllers

import javax.inject._
import akka.actor.{ActorRef, ActorSystem, Props}
import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout
import com.newrelic.api.agent.{NewRelic, Trace}

import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param cc standard controller components
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.  When rendering content, you should use Play's
 * default execution context, which is dependency injected.  If you are
 * using blocking operations, such as database or network access, then you should
 * use a different custom execution context that has a thread pool configured for
 * a blocking API.
 */
@Singleton
class AsyncController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {
  import scala.concurrent.duration._
  val myActor = actorSystem.actorOf(Props[MyActor], "my-actor")
  implicit val timeout: Timeout = Timeout(10 seconds)
  /**
   * Creates an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  @Trace(metricName= "message", dispatcher = true)
  def message: Action[AnyContent] = Action.async {
    val transaction = NewRelic.getAgent.getTransaction
    val id = NewRelic.getAgent.getTraceMetadata.getTraceId
    println(NewRelic.getAgent.getTransaction)
    println(s"zzzzzzzzzzzzzzz transaction id on controller: $id")
    Await.result(myActor ? "test", 10 seconds)
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success("Hi Zeta!")
    }(actorSystem.dispatcher) // run scheduled tasks using the actor system's dispatcher
    promise.future
  }

}
