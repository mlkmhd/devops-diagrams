# RabbitMQ Scraping

As you can see in bellow diagram, there are 2 way to scrape metrics from rabbitmq server. one of the is using rabbitmq exporter and the second one is scraping metrics directly from prometheus without using any exporter. This feature can reduce the cost of resource usage. In this article I'll guide you for the second approach.


## Step 1 (Traditional way)
<p align="center">
  <img src="pictures/architecture.jpg?raw=true" />
</p>

you can read the full article at the following blog post: https://medium.com/itnext/stop-using-rabbitmq-exporter-5817d39dc236
