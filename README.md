# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```



### Server Design
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USgAiOUoTuxTuduqdssZ9B9gc9YuwQvwZAN+IAFjbHc71TUwEefZgneBUHMcRgug0MGmAEcLKowJ3J9Xp4N6xhFy2AMwrtdcDe9+I7vfQUsIFkNGca5M2vKtqBrGcH0bGAAFZl1KVc7k-Lcf33eINFUZliDGMD0A0EwzEsawbGgaRHhgUEIE4NAfH8QJMGg-4p3iZI0iyXJ9DUfEOx7Hk1g2DQbwg6dIh1Klk0JFB4iomibW9eVeUEg5xNzA0LmNYUHiedkoHkz5o0dWMRW0GB4jgXwUBAIMFT5TYIzQY8ey9GQFBSQyfmFQtVL1PNzniCVnlBUFs1xXz1IiTS-lNFBWQtK19LtB1POdSIjDMmBnmwLRAkyj47RgJAMAgGB4t8Vz3KiuMIh804UwMeIux7cdB23EcKzQFqelq6l6trFiYCa-Lqhaod2tA-swKnWtZxCGDW3bRDmsmsb0w6rrwP6KC50fGAlwARjfZDN2-Xd0Ohex-WgJAAC8UA0Kbbxm6D4ngxau3fFDTt-A9MK4bDsFwnp8MI8wrFsMwUFDWTLGYGw-ACIIduYQYBoSGQwWBdJgWyHIuK4HjLqtUcs2mhMc3C+qZOo2G5nG9AVMTSmpI0mMMQyzCECeFAyrptbMz2GAPKdOM0tM+IZBQLnAkxMqKuF4zvKZuqWYCoKQp6yT81pQsBsC4LNsg+9kebNtDbvOsTb2ltDsQz6Tu3M6-wustrruh7hK2425tehCPuOr9HZ+jCsKgHD8TwxzQeI2xpmPUE3BgABxO1MXh+ikbm5jb1YpOsdx9g7WKenSae8mwpV7XpMotwU7XPniY6xmKcrgtwiqjLkB4OvVAb8sBYV9mXUBNhgGPHuE54GBvnhREYEL6p5Y7pWW96qS1YN5eauVteq4yrsF9ULcEgPu0xRkLd9qbZ8FyHBHAl05ZRu3bQEFAINH7tZ-O0PiU7UmroMBMjdR3lrAsEQBqnzXMfKBKBz6X2vrfbc982TDS-AOIcr936fxGitbcv9-4TiAZ7I2lsfYwAWiuQ+XAYE-zPhfeIV8b53wzjg9BrVOxYOsmw7+BDqgAOIebZ6VsDpHXXA7NCzsLBE18G7e6j0RLCPIW9MRH4JFO1+qHcOghI4EVMGDEi2ALBQGwFzeAZoDA9zoojRiyNs4iVYqkDIBci4yJJh2PhKASGiSGBXXekxzGxUCD3OYnjm5+LATrS4bM7jaTACEsJg8vJi1iKPY8cJoDJwKn-aos8IBIkPkvGJzpy66lbrSJ8LZcgwH1hrUBfkolVRiqyBJdocn2iSalAEsQ4AWJgGgFA3gsm5KKvPO0RSjJD23qvSJ1dYHwMYYgkBMyGmGggTnQadDqgLJgEwhc3ilHzgoX7LZcCGG7KWUI7a5ClzvSQuIwOkiDxHhPCgc8l4FFezIUc0RdsA6oQ0fEaRrsw7u0+aQ2aRyVF-IeQC4OhgtGAwjsDKO+iY42EcFLBAEAhkACkICjKsTYLhQZbFZ1Rhs5IzwOI5EPsXfm6AOzQTgBAbFUA6iH3Pgc0pElVnxHxUVEJzLWXQA5fQ8JZT-GNOKfceeTxWnbJkMlEWFxRQUC5uAAA5JiKxCJ8ljMXm5Tp1UxL1INMSUk5JNarNZpM2J0JsotLtKE+hdRhVsuVYrcIooso5WrmKNABRmC6RgJAYZBhpDHhZWyzKjrcqLAqjAAAZC5I1W9TUrPNf0y1oVJWzNtSlWVVg-UKrOZ6oe3rAS+tZOKQNlAwCYhDWGqxkaYAQGwKGaNmTRmFLcsm1NlUZUzWtVmkkZJjXDrNVTXNvKIrrIcVc72RyFqLu+btW5qivpB3Oi8s8F4eDgotpC9dNtN3qPhcCq6oL5HcqXSe18MK1GPMBRGACsUgKhuAKBW9a6YLQv9rC7650-oAyBpgEGaLwY2DMMAZwiBYqwGANgUxhBfLWIYkxClDjEgY3BNjXGRgrkZoiXy2cCGFDIeQCAXycwJWzr6kaGVTIuZ4FoxO4eEspbc1Kv6CZhaTKcelgYXSfGVUcenlx3KPd5bpRnE09jPK1LTpHQxrDMRYiruPfNM2ZM71-pOfbZ98KQNhyRTolFBEgA
