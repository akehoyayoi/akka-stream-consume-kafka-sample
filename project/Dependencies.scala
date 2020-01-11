import sbt._

object Dependencies {

  val AkkaVersion = "2.5.23"
  val AlpakkaKafkaVersion = "1.1.0"

  lazy val deps: Seq[ModuleID] = Seq(
    // akka
    "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,

    // refs: https://issues.apache.org/jira/browse/KAFKA-9210?jql=project%20%3D%20KAFKA%20AND%20issuetype%20%3D%20Bug%20AND%20status%20in%20(Open%2C%20%22In%20Progress%22)%20AND%20text%20~%20%22loss%20data%22
    ("com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion).exclude("org.apache.kafka", "kafka-clients"),
    "org.apache.kafka" % "kafka-clients" % "2.4.0",
  )
}
