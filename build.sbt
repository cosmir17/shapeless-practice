name := "shapeless-practice"

version := "0.1"

scalaVersion := "2.13.0-M5"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "com.chuusai" %% "shapeless" % "2.3.3",
  
  "org.scalatest" %% "scalatest" % "3.0.6" % "test",
  "junit" % "junit" % "4.12" % "test"
)