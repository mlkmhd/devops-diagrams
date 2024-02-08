# Kafka Cluster without Zookeeper
you can use the following ways to bring up a kafka cluster

## docker-compose
for bringing up the kafka cluster using docker-compose you can run the following command:

```bash
$ docker-compose up -d -f docker-compose.yaml
```

## Helm Chart
for bringing up the kafka cluster using Helm Chart you can run the following command:
```bash
$ helmfile apply -f helmfile.yaml
$ 
$ kubectl -n test get service
NAME                        TYPE        CLUSTER-IP        EXTERNAL-IP   PORT(S)                      AGE
kafka                       ClusterIP   10.43.54.217      <none>        9092/TCP                     3d4h
kafka-controller-headless   ClusterIP   None              <none>        9094/TCP,9092/TCP,9093/TCP   3d4h
```

for example if you want to connect spring boot application to the above kafka server, you can use the following configuration:
```commandline
spring.kafka.bootstrap-servers=kafka:9092
spring.kafka.properties.security.protocol=SASL_PLAINTEXT
spring.kafka.properties.sasl.mechanism=PLAIN
spring.kafka.properties.sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="user1" password="xxxxxxx";
```
you can find the password that have been used in the above configuration in the following secret:
```commandline
kubectl -n ecfd-devops-spring-cloud-config edit secrets kafka-user-passwords
```

you can find the full article in the following link:
https://medium.com/itnext/kafka-cluster-without-zookeeper-ca40d5f22304
