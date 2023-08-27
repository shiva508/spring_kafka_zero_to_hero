
# Open kafka docker console
## docker exec -it broker /bin/bash

# List kafka topic
## kafka-topics --list --bootstrap-server broker:9092

# Consuming topic
## kafka-console-consumer --bootstrap-server broker:9092 --topic first-topic --from-beginning --property parse.key=true
## kafka-console-consumer --bootstrap-server broker:9092 --topic news-11-count-topic --from-beginning --property parse.key=true --property key.separator=: --property print.key=true --property print.value=true --property print.offset=true

# Delete Topic
## kafka-topics --bootstrap-server broker:9092 --delete --topic names-topic,news-0-count-topic



