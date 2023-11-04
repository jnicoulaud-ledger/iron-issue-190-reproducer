inThisBuild(
  scalaVersion := "3.3.1"
)

lazy val api = Project("api", file("modules/api"))
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel"               %% "cats-core"   % "2.10.0",
      "com.softwaremill.sttp.tapir" %% "tapir-core"  % "1.8.5",
      "com.softwaremill.sttp.tapir" %% "tapir-iron"  % "1.8.5",
      "io.circe"                    %% "circe-core"  % "0.14.6",
      "org.scodec"                  %% "scodec-core" % "2.2.2",
      "org.scodec"                  %% "scodec-bits" % "1.1.38",
      "io.github.iltotore"          %% "iron"        % "2.3.0",
      "io.github.iltotore"          %% "iron-cats"   % "2.3.0",
      "io.github.iltotore"          %% "iron-circe"  % "2.3.0"
    )
  )
