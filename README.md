# Kafka Apache

## Overview
Kafka Application

## Pre-requisites
1. Instructions sourced from [dzone.com](https://dzone.com/articles/running-apache-kafka-on-windows-os) 
   1. Download Server JRE according to your OS and CPU architecture from http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
   2. Download and extract ZooKeeper using 7-zip from http://zookeeper.apache.org/releases.html
   3. Download and extract Kafka using 7-zip from http://kafka.apache.org/downloads.html

2. Docker-compose
3. Maven
4. App OCEP Simulator
5. Java 11

# Getting started

## Configuring Zookeeper 
1. Go to your ZooKeeper config directory. For me its C:\zookeeper-3.4.7\conf

2. Rename file “zoo_sample.cfg” to “zoo.cfg”

3. Open zoo.cfg in any text editor, like Notepad; I prefer Notepad++.

4. Find and edit dataDir=/tmp/zookeeper to :\zookeeper-3.4.7\data

5. Add an entry in the System Environment Variables as we did for Java.

    1. Add ZOOKEEPER_HOME = C:\zookeeper-3.4.7 to the System Variables.

   2. Edit the System Variable named “Path” and add ;%ZOOKEEPER_HOME%\bin;

6. You can change the default Zookeeper port in zoo.cfg file (Default port 2181).

7. Run ZooKeeper by opening a new cmd and type zkserver.

## Configuring Kafka

1. Go to your Kafka config directory. For me its C:\kafka_2.12-3.9.0\config

2. Edit the file “server.properties.”

3. Find and edit the line log.dirs=/tmp/kafka-logs” to “log.dir= C:\kafka_2.12-3.9.0\kafka-logs.

4. If your ZooKeeper is running on some other machine or cluster you can edit “zookeeper.connect:2181” to your custom IP and port. For this demo, we are using the same machine so there's no need to change. Also the Kafka port and broker.id are configurable in this file. Leave other settings as is. Learn how to setup a Zookeeper cluster.

5. Your Kafka will run on default port 9092 and connect to ZooKeeper’s default port, 2181.

## Run Kafka Server
#### Important: Please ensure that your ZooKeeper instance is up and running before starting a Kafka server.

1. Go to your Kafka installation directory: C:\kafka_2.12-3.9.0

2. Open a command prompt here by pressing Shift + right click and choose the “Open command window here” option).

3. Now type .\bin\windows\kafka-server-start.bat .\config\server.properties and press Enter.


### How to build
Once your environment is set up
#### Creating Topics
1. Now create a topic with the name “test” and a replication factor of 1, as we have only one Kafka server running. If you have a cluster with more than one Kafka server running, you can increase the replication-factor accordingly, which will increase the data availability and act like a fault-tolerant system.

2. Open a new command prompt in the location C:\kafka_2.11-0.9.0.0\bin\windows.

3. Type the following command and hit Enter: 
 kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

#### Creating a Producer and Consumer to Test Server
1. Open a new command prompt in the location C:\kafka_2.11-0.9.0.0\bin\windows

2. To start a producer type the following command:
kafka-console-producer.bat --broker-list localhost:9092 --topic test
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test

a. List topics bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
b. Describe Topic: bin\windows\kafka-topics.bat --describe --bootstrap-server localhost:9092 --topic lunch_orders
c. Read messages from the beginning: bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --topic lunch_orders --from-beginning
## Some Other Useful Commands
List Topics: kafka-topics.bat --list --zookeeper localhost:2181
Describe Topic: kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]

Before version < 2.0: kafka-console-consumer.bat --zookeeper localhost:2181 --topic [Topic Name] --from-beginning
After version > 2.0:  kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic [Topic Name] --from-beginning
Delete Topic: kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181
http://localhost:8084/api/v1/order/index

http://localhost:8084/swagger-ui/index.html

docker compose up -d
docker compose restart control-center
seipati@uNovakaPati:~/kafka-new$ docker compose restart control-center
--bootstrap-server localhost:9092 --delete --topic '.*'




create port
lt --port 8000
curl https://loca.lt/mytunnelpassword

https://loca.lt/mytunnelpassword

```bash
$ mvn clean install
```

### How to format the code

```bash
$ mvn git-code-format:format-code
```

### How to run tests

```bash
$ mvn clean tests
```

### How to run integration tests

*NOTE: You may need to add the environment variable as shown in the screenshot below if you want to run the integration tests *

![Screenshot](docs/images/testcontainers-disable-ryuk.PNG)

```bash
$ mvn clean integration-tests
```

### How to run locally

```bash
$ docker-compose up -d
```






```bash
# Start up containers in foreground
$ docker-compose up
[+] Running 5/3
 ⠿ Network rpp-ocep_app-tier             Created                                                                                                                                                            0.0s
 ⠿ Container rpp-ocep-redis-1            Created                                                                                                                                                            0.1s
 ⠿ Container rpp-ocep-rppocepdb-1        Created                                                                                                                                                            0.1s
 ⠿ Container rpp-ocep-redis-commander-1  Created                                                                                                                                                            0.0s
 ⠿ Container pmntrpp.app                 Created                                                                                                                                                            0.0s
Attaching to pmntrpp.app, rpp-ocep-redis-1, rpp-ocep-redis-commander-1, rpp-ocep-rppocepdb-1
....

# Start up containers in background
$ docker-compose up -d
[+] Running 4/4
 ⠿ Container rpp-ocep-rppocepdb-1        Started                                                                                                                                                            0.7s
 ⠿ Container pmntrpp.app                 Started                                                                                                                                                            2.0s
 ⠿ Container rpp-ocep-redis-1            Started                                                                                                                                                            0.7s
 ⠿ Container rpp-ocep-redis-commander-1  Started


# Cleanup the containers
$ docker-compose down
[+] Running 5/5
 ⠿ Container rpp-ocep-redis-commander-1  Removed                                                                                                                                                            0.4s
 ⠿ Container pmntrpp.app                 Removed                                                                                                                                                            1.2s
 ⠿ Container rpp-ocep-redis-1            Removed                                                                                                                                                            0.7s
 ⠿ Container rpp-ocep-rppocepdb-1        Removed                                                                                                                                                            0.5s
 ⠿ Network rpp-ocep_app-tier             Removed
 
 # View logs (docker-compose logs <service-name>)
 # Use -f to tail the logs
 $ docker-compose logs pmntrpp.app -f
 ... some logs ....
```

## Debugging

Since we are running the application inside a Docker container, you can create a remote debug configuration.

Example shown below.

![Screenshot](docs/images/remote-debug-config.PNG)









## APP OCEP Simulator

Set up the APP OCEP simulator as documented here - https://confluence.fnbconnect.co.za/display/PE/OCEP+Simulator

## Configuring Simulator Extension Config

![Screenshot](docs/images/rpp-ocep-extension-config.png)

Please configure the extension URL as per your deployment, the highlighted example is for `docker-compose`. If you are running the platform extension simulator and the OCEP application in the same application server locally, you can configure the extension URL to be *http://localhost:8080/pmntrpp*.


## Zookeeper

Once your environment is set up
```bash
# Start up containers in foreground
$ docker-compose up
[+] Running 5/3
 ⠿ Network rpp-ocep_app-tier             Created                                                                                                                                                            0.0s
 ⠿ Container rpp-ocep-redis-1            Created                                                                                                                                                            0.1s
 ⠿ Container rpp-ocep-rppocepdb-1        Created                                                                                                                                                            0.1s
 ⠿ Container rpp-ocep-redis-commander-1  Created                                                                                                                                                            0.0s
 ⠿ Container pmntrpp.app                 Created                                                                                                                                                            0.0s
Attaching to pmntrpp.app, rpp-ocep-redis-1, rpp-ocep-redis-commander-1, rpp-ocep-rppocepdb-1
....

# Start up containers in background
$ docker-compose up -d
[+] Running 4/4
 ⠿ Container rpp-ocep-rppocepdb-1        Started                                                                                                                                                            0.7s
 ⠿ Container pmntrpp.app                 Started                                                                                                                                                            2.0s
 ⠿ Container rpp-ocep-redis-1            Started                                                                                                                                                            0.7s
 ⠿ Container rpp-ocep-redis-commander-1  Started


# Cleanup the containers
$ docker-compose down
[+] Running 5/5
 ⠿ Container rpp-ocep-redis-commander-1  Removed                                                                                                                                                            0.4s
 ⠿ Container pmntrpp.app                 Removed                                                                                                                                                            1.2s
 ⠿ Container rpp-ocep-redis-1            Removed                                                                                                                                                            0.7s
 ⠿ Container rpp-ocep-rppocepdb-1        Removed                                                                                                                                                            0.5s
 ⠿ Network rpp-ocep_app-tier             Removed
 
 # View logs (docker-compose logs <service-name>)
 # Use -f to tail the logs
 $ docker-compose logs pmntrpp.app -f
 ... some logs ....
```

## Debugging

Since we are running the application inside a Docker container, you can create a remote debug configuration.

Example shown below.

![Screenshot](docs/images/remote-debug-config.PNG)

## How to generate the PNG from PDF

```bash
# Download the pdfbox jar
wget https://dlcdn.apache.org/pdfbox/2.0.26/pdfbox-app-2.0.26.jar

# Run PDFToImage
java -jar pdfbox-app-2.0.26.jar PDFToImage -imageType png -dpi 120 RPP_Electronic_Payment_Terms_and_Conditions_Retail.pdf
```
You can read me more about pdf to box - https://pdfbox.apache.org/2.0/commandline.html#pdftoimage

## Limitations

1. Code hot swapping or reloading is not available at the moment.


