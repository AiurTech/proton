package uk.co.aiur.proton

import java.io.File

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions, ConfigResolveOptions}
import org.slf4j.Logger

object ConfigLoader {
  val configParseOptions   = ConfigParseOptions.defaults().setAllowMissing(false)
  val configResolveOptions = ConfigResolveOptions.defaults().setAllowUnresolved(false)

  def apply(configFilePath: Option[String])(log: Logger): Config = {
    val config = configFilePath match {
      case None =>
        ConfigFactory.load(
          this.getClass.getClassLoader(),
          "application.conf",
          configParseOptions,
          configResolveOptions
        )
      case Some(filePath) =>
        ConfigFactory
          .parseFile(new File(filePath), configParseOptions)
          .withFallback(ConfigFactory.load())
    }
    log.debug(s"Config: ${config.origin()}")
    config.resolve()
  }
}
