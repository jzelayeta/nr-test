package controllers

import com.newrelic.api.agent.{NewRelic, Trace}
import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  @Trace
  def index = Action {
    val transaction = NewRelic.getAgent.getTransaction
    val id = NewRelic.getAgent.getTraceMetadata.getTraceId
    println(System.getenv("javaagent"))
    println(NewRelic.getAgent.getTransaction)
    println(s"zzzzzzzzzzzzzzz transaction id is: $id")
    Ok("HOLAAAAAAAAAA")
  }

}
