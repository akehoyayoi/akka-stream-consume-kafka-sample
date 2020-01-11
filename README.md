## How to run the service

This application requires a few services in order to run locally.


### Required dependencies

- Docker (Version 18 or above)
- Sbt (Version 0.13 or above)

## Make commands for ease of development

Please note that the optional commands are not to be run in the order of the commands listed below.
They are in case you want to explore more about a particular section.

**Please make sure to read what the command is supposed to do from Makefile before running/using it.**

### Starting docker containers
```bash
make start-containers
```

### Stop docker containers (Optional)
```bash
make stop-containers
```

### Destroy docker containers (Optional)
```bash
make destroy-containers
```

### Setup kafka & sqs before running the application with:

#### Create kafka topics

You can create a particular topic with:
```bash
make kafka-create-topic topic=$topic
```
where $topic is the topic name. For example: "com.yoheiokaya"

#### Configure kafka topics (Optional)
```bash
make kafka-configure-topic topic=$topic
```

#### Describe kafka topics (Optional)
```bash
make kafka-describe-topic topic=$topic
```

#### Delete kafka topics (Optional)
```bash
make kafka-delete-topic topic=$topic
```

#### Reset kafka topic offsets (Optional)
```bash
make kafka-reset-offsets topic=$topic
```

#### Run kafka consumer for a topic (Optional)
```bash
make kafka-consumer topic=$topic
```