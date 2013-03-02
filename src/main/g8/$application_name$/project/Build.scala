import _root_.java.net.URL
import _root_.sbt.Keys._
import _root_.sbt.Resolver

object ApplicationBuild extends Build {

  val appName = "$application_name$"
  val appVersion = "1.0"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc
    // Play slick
    , "com.typesafe" % "play-slick_2.10" % "0.2.7-SNAPSHOT"
    , "mysql" % "mysql-connector-java" % "5.1.23"
    // enables logging
    , "org.slf4j" % "slf4j-api" % "1.7.2"
    , "ch.qos.logback" % "logback-classic" % "1.0.9"
    // metrics
    , "nl.grons" % "metrics-scala_2.10" % "2.2.0"
    //    , "com.yammer.metrics" % "metrics-scala_2.10" % "2.2.0"
    //    , "com.yammer.metrics" % "metrics-graphite" % "2.2.0"
    // Webjars
    , "org.webjars" % "webjars-play" % "2.1-0"
    //    , "org.webjars" % "requirejs" % "2.1.1"
    , "org.webjars" % "bootstrap" % "$bootstrap_version$"
    , "org.webjars" % "momentjs" % "1.7.2"
    , "org.webjars" % "angularjs" % "1.1.3"
    , "org.webjars" % "angular-ui" % "0.3.2-1"
    , "org.webjars" % "angular-strap" % "0.6.6"
    , "org.webjars" % "font-awesome" % "3.0.0"
    , "org.webjars" % "jquery-ui" % "$jqueryui_version$"
    , "org.webjars" % "tinymce-jquery" % "3.4.9"

  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    // Add your own project settings here
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
  ).dependsOn(RootProject(uri("git://github.com/freekh/play-slick.git")))

}
