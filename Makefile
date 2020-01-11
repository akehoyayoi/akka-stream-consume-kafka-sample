SHELL := /bin/bash

.PHONY: start-containers stop-containers destroy-containers

start-containers:
	docker-compose up -d --build

stop-containers:
	docker-compose stop

destroy-containers:
	docker-compose down --volumes

.PHONY: kafka-consumer

kafka-consumer:
	docker-compose exec kafka \
	kafka-console-consumer --bootstrap-server kafka:9092 \
		--topic $(topic) --from-beginning

.PHONY: kafka-create-topic kafka-configure-topic kafka-describe-topic kafka-delete-topic

kafka-create-topic:
	docker-compose exec kafka \
		kafka-topics --zookeeper zookeeper:2181 \
			--create \
			--topic $(topic) \
			--replication-factor 1 \
			--partitions 1 \
			--config retention.ms=-1

kafka-configure-topic:
	docker-compose exec kafka \
		kafka-topics --zookeeper zookeeper:2181 \
			--alter \
			--topic $(topic) \
			--partitions 1

kafka-describe-topic:
	docker-compose exec kafka \
		kafka-topics --zookeeper zookeeper:2181 \
			--describe \
			--topic $(topic)

kafka-delete-topic:
	docker-compose exec kafka \
		kafka-topics --zookeeper zookeeper:2181 \
			--delete \
			--topic $(topic)

.PHONY: kafka-reset-offsets

kafka-reset-offsets:
    docker-compose exec kafka \
        kafka-consumer-groups --zookeeper zookeeper:2181 \
            --group clm-santa-engine-1 \
            --reset-offsets --to-earliest \
            --topic $(topic) --execute

.PHONY: publish-hello

publish-hello:
	docker-compose exec -T kafka \
		kafka-console-producer --broker-list localhost:9092 \
			--topic com.yoheiokaya < data/hello.json
