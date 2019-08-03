package uk.co.aiur.proton

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.io.StdIn

object Server {

  val systemName = "Proton"
  val serverHost = "localhost"
  val serverPort = 8080

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem             = ActorSystem(systemName)
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext                = system.dispatcher

    val bindingFuture = Http().bindAndHandle(Routes.statusRoute, serverHost, serverPort)

    println(s"Server online at http://$serverHost:$serverPort/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind())                 // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
