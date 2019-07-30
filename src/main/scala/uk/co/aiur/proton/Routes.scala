package uk.co.aiur.proton

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

object Routes {

  val statusRoute =
    path("status") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Online</h1>"))
      }
    }
}
