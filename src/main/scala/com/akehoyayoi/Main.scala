package com.akehoyayoi

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.Subscriptions
import akka.kafka.scaladsl.Consumer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.{ExecutionContextExecutor, Future}

object Main {

  val config: Config = ConfigFactory.load()
  /** Kafka Configuration **/
  val kafkaConsumerConfig: Config = config.getConfig("akka.kafka.consumer")

  /** Akka System initializer **/
  implicit val system: ActorSystem = ActorSystem("application")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  def business(key: String, value: String): Future[Done] = {
    println(s"key=${key}, value=${value}")
    Future(Done)
  }

  def main(args: Array[String]): Unit = {

    Consumer.committableSource(Kafka.configuration(kafkaConsumerConfig),
      Subscriptions.topics("com.yoheiokaya"))
      .mapAsync(1) { message ⇒
        business(message.record.key(),message.record.value()).map(_ ⇒ message)
      }
      .via(Kafka.ack(1,1))
      .runWith(Sink.ignore)

  }

}
