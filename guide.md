
# Open kafka console
## docker exec -it broker /bin/bash

# List kafka topic
## kafka-topics --list --bootstrap-server broker:9092

# Consuming topic
## kafka-console-consumer --bootstrap-server broker:9092 --topic first-topic --from-beginning --property parse.key=true
## kafka-console-consumer --bootstrap-server broker:9092 --topic third-topic --from-beginning --property parse.key=true --property key.separator=: --property print.key=true --property print.value=true --property print.offset=true
## kafka-topics --bootstrap-server broker:9092 --delete --topic names-topic,news-0-count-topic,news-0-topic,news-1-count-topic,news-1-topic,news-count-topic,news-topic,second-topic,third-topic,word-count-topic,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000002-changelog,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000004-changelog,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000004-repartition,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000005-changelog,wordCountProcess-applicationId-KSTREAM-AGGREGATE-STATE-STORE-0000000005-repartition,wordCountProcess-applicationId-KTABLE-SUPPRESS-STATE-STORE-0000000005-changelog,wordCountProcess-applicationId-KTABLE-SUPPRESS-STATE-STORE-0000000009-changelog,wordCountProcess-applicationId-KTABLE-SUPPRESS-STATE-STORE-0000000010-changelog,wordCountProcess-applicationId-KTABLE-SUPPRESS-STATE-STORE-0000000011-changelog



