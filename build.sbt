inThisBuild(
  scalaVersion := "3.3.1"
)

lazy val api = project.in(file("modules/api"))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.10.0",
      "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.8.5",
      "com.softwaremill.sttp.tapir" %% "tapir-iron" % "1.8.5",
      "io.github.iltotore" %% "iron" % "2.3.0",
      "io.github.iltotore" %% "iron-cats" % "2.3.0",
    )
  )
