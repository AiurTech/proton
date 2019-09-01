package uk.co.aiur.proton.routes

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.{Http, HttpExt}

class StockRoutes(http: HttpExt, baseUrl: String) extends RouteSpec {
  val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  override val routes = {
    path("stocks" / Segment) { stock =>
      get {
        complete {
          val todaysDate = ZonedDateTime.now.format(isoDate)
          getLatestPrice(stock)(todaysDate)
        }
      }
    }
  }

  private def getLatestPrice(stock: String)(date: String) = {
    val fullUrl = baseUrl.format(stock, date)
    val request = HttpRequest(method = HttpMethods.GET, uri = fullUrl)
    http.singleRequest(request)
  }

  override def restEndpoints: List[String] =
    List("GET /stocks/<stock> := String => Json")
}

object StockRoutes {

  val quandlStockUrlTemplate =
    "https://www.quandl.com/api/v3/datasets/wiki/%s.json?limit=1&end_date=%s"

  def apply(
    baseUrl: String = quandlStockUrlTemplate
  )(
    implicit actorSystem: ActorSystem
  ): StockRoutes =
    new StockRoutes(Http(), baseUrl)
}
