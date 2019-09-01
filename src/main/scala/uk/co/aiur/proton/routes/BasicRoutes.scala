package uk.co.aiur.proton.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

trait BasicRoutes extends RouteSpec {

  override def routes = basicRoutes

  val basicRoutes =
    path("status") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Online</h1>"))
      }
    } ~
      path("echo-input") {
        post {
          entity(as[String]) { input =>
            println(s"Received: $input")
            complete(HttpEntity(ContentTypes.`application/json`, input))
          }
        }
      }

  override def restEndpoints: List[String] =
    List(
      "GET /status := () => String",
      "POST /echo-input := Json => Json"
    )

}

object BasicRoutes extends BasicRoutes
