lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-starter-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "com.newrelic.agent.java" % "newrelic-api" % "6.3.0",
      "com.typesafe.akka" %% "akka-actor" % "2.6.5",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings",
    ),
    javaOptions ++= Seq("-javaagent:/Users/julianzelayeta/Downloads/play-samples-play-scala-starter-example/newrelic/newrelic.jar")
  )
