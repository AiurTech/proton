package uk.co.aiur.proton.routes

import akka.http.scaladsl.server.Route

trait RouteSpec {
  def routes: Route
  def restEndpoints: List[String]
}
