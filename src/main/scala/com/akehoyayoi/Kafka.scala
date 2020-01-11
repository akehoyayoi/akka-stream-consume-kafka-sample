package com.akehoyayoi

import akka.{Done, NotUsed}
import akka.kafka.ConsumerMessage.{CommittableMessage, CommittableOffsetBatch}
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.{Flow, Source}
import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration

object Kafka {

  type KafkaCommittableMessage = CommittableMessage[String, String]

  private val bootstrapServer: String = "localhost:9092"
  private val offsetReset: String = "latest"
  private val consumerGroup: String = "consumer-group-1"

  def configuration(config: Config): ConsumerSettings[String, String] =
    ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(bootstrapServer)
      .withGroupId(consumerGroup)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset)
      .withStopTimeout(Duration.Zero) // refs: https://doc.akka.io/docs/alpakka-kafka/current/consumer.html#draining-control

  def createSource(config: ConsumerSettings[String, String],
                   topics: Seq[String]): Source[KafkaCommittableMessage, Consumer.Control] =
    Consumer.committableSource(config, Subscriptions.topics(topics.toSet))

  def ack(batchSize: Int, parallelism: Int)(implicit ec: ExecutionContext): Flow[KafkaCommittableMessage, Done, NotUsed] =
    Flow[KafkaCommittableMessage]
      .map { _.committableOffset }
      .batch(max = batchSize, CommittableOffsetBatch.apply)(_.updated(_))
      .mapAsync(parallelism) { _.commitScaladsl() }
}
