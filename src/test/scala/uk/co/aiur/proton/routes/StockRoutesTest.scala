package uk.co.aiur.proton.routes

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.Mockito
import org.scalatest.{FreeSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.Future
import scala.io.Source

class StockRoutesTest extends FreeSpec with Matchers with ScalatestRouteTest with MockitoSugar {
  "StockRoutes" - {
    "return a given Stock from Quandl for GET requests to /stock/<stock> path" in {
      val mockHttp = mock[HttpExt]

      val requestTemplate = StockRoutes.quandlStockUrlTemplate
      val stock = "AAPL"
      val miniResponse = Source.fromResource(s"stock.$stock.json").getLines().mkString
      val response = HttpResponse(StatusCodes.OK, entity = HttpEntity(miniResponse))
      Mockito.when(mockHttp.singleRequest(Get(requestTemplate.format(stock, "2019-09-01")))).thenReturn(
        Future.successful(response))

      val stockRoutes = new StockRoutes(mockHttp, requestTemplate).routes
      Get(s"/stocks/$stock") ~> stockRoutes ~> check {
        responseAs[String] shouldEqual miniResponse
      }
    }
  }
}

