# qrmi-adapter

> UNDER CONSTRUCTION

# Overview

The objective is to apply an adapter into an existing (legacy) AQMP framework (producers, consumers, request-response service, etc) with minimal impact to existing code baseline. The adapter would better utilize interfaces from Spring AMQP/RabbitMQ to tasks such as serialization.

# Resources

1. [RemoteInvocationSerializingExporter](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/remoting/rmi/RemoteInvocationSerializingExporter.html)