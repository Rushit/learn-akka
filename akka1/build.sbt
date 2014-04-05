organization := "com.rpatel"

name := "akka1"

version := "1.0"

scalaVersion := Version.scala

libraryDependencies ++= Dependencies.akkaLearn

unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := List((scalaSource in Test).value)

scalacOptions ++= List("-unchecked","-deprecation","-language:_","-encoding", "UTF-8")

mainClass in (Compile,run) := Some("com.rpatel.learn.akka.Main")

javaOptions += "-XX:Xmx64m"

