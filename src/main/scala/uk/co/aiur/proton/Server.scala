package uk.co.aiur.proton

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import java.util.concurrent.TimeUnit

import akka.http.scaladsl.server.Route
import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import uk.co.aiur.proton.routes.{BasicRoutes, RouteSpec, StockRoutes}

import scala.concurrent.{ExecutionContext, Future}

object Server {
  val log = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    implicit val config: Config = ConfigLoader(args.headOption)(log)
    val maxLifetimeInSeconds    = config.getDuration("server.lifetime")
    val server                  = ServerInstance(config)
    val runtime                 = server.run()
    log.info(s"Server will shutdown in ${maxLifetimeInSeconds.getSeconds} seconds")
    TimeUnit.SECONDS.sleep(maxLifetimeInSeconds.getSeconds)
    server.stop(runtime)
  }
}

class ServerInstance(
  routes: Route
)(
  implicit system: ActorSystem,
  materializer: ActorMaterializer,
  ec: ExecutionContext,
  config: Config) {

  import ServerInstance._
  val serverHost = getStr("server.host")
  val serverPort = config.getInt("server.port")

  private val server = Http()

  def run(): Future[Http.ServerBinding] = {
    val bindingFuture = server.bindAndHandle(routes, serverHost, serverPort)
    log.info(s"Server online at http://$serverHost:$serverPort/")
    bindingFuture
  }

  def stop(bindingFuture: Future[Http.ServerBinding]): Unit = {
    log.info(s"Shutdown")
    bindingFuture
      .flatMap(_.unbind())                 // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  private def getStr(path: String): String =
    config.getString(path)
}

object ServerInstance {

  def apply(implicit config: Config): ServerInstance = {
    implicit val actorSystem: ActorSystem           = ActorSystem(config.getString("system.name"))
    implicit val materializer: ActorMaterializer    = ActorMaterializer()
    implicit val executionContext: ExecutionContext = actorSystem.dispatcher

    val routes           = makeRoutes()
    val allRoutes: Route = makeRoute(routes)
    new ServerInstance(allRoutes)
  }

  private def makeRoutes()(implicit actorSystem: ActorSystem): List[RouteSpec] =
    List(BasicRoutes, StockRoutes())

  private def makeRoute(allRoutes: List[RouteSpec]) = {
    import akka.http.scaladsl.server.Directives._
    log.debug(s"Endpoints")
    allRoutes.flatMap(_.restEndpoints).foreach { log.debug }
    allRoutes.map(_.routes).reduce { (a, b) =>
      a ~ b
    }
  }

  val log = LoggerFactory.getLogger(getClass)
}
