package uk.co.aiur.proton.routes

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FreeSpec, Matchers}

class BasicRoutesTest extends FreeSpec with Matchers with ScalatestRouteTest {
  "BasicRoutes" - {
    "return status online for GET requests to /status path" in {
      Get("/status") ~> BasicRoutes.routes ~> check {
        responseAs[String] shouldEqual "<h1>Online</h1>"
      }
    }
    "return the same input for POST requests to /echo-input path" in {
      Post("/echo-input", "test content") ~> BasicRoutes.routes ~> check {
        responseAs[String] shouldEqual "test content"
      }
    }
  }
}
