# spring-async-demo

## Overview

This PoC demonstrates the following architecture, design, and coding strategies:

1. Spring Application Events processed asynchronously on virtual threads.
1. Spring Scheduler Leveraging Fixed Rate Batch and Asynchronous Processing. 
1. Stream Event Triggering and Processing leveraging `RabbitTemplate`.

## Features

- Job Processing Application
- Jobs Stored In Memory Repository
- Create Jobs
    ```
    POST /jobs HTTP/1.1
    Host: localhost:8080
    ```
- Get Jobs to View Current Status
    ```
    GET /jobs HTTP/1.1
    Host: localhost:8080
    ```
- Update status on an existing Job
    ```
    POST /jobs/1 HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    {
    	"status": "FAILED"
    }
    ```
  
Create Job triggers an Application Event that then processes the job on a background virtual thread.  Edit Job triggers an Event on an AMQP event stream that is then consumed which processes the Job on a background rabbit virtual thread.

Sample Logging Output for Job Creation:
```text
2024-07-10T10:41:44.104-05:00  INFO 35811 --- [async-demo] [tomcat-handler-0] c.example.asyncdemo.job.AsyncController  : Starting job creation
2024-07-10T10:41:44.104-05:00  INFO 35811 --- [async-demo] [tomcat-handler-0] c.e.asyncdemo.job.AsyncDemoService       : Creating Job
2024-07-10T10:41:44.105-05:00  INFO 35811 --- [async-demo] [  virtual-task-1] c.e.a.event.AsyncDemoEventListener       : Processing Application Event for Job 1. VT: true
2024-07-10T10:41:44.105-05:00  INFO 35811 --- [async-demo] [  virtual-task-1] c.e.asyncdemo.job.AsyncDemoService       : Processing Job in PROCESSING Status on Iteration 1. VT: true
2024-07-10T10:41:44.105-05:00  INFO 35811 --- [async-demo] [tomcat-handler-0] c.e.asyncdemo.job.AsyncDemoService       : Completed Creating job 1
2024-07-10T10:41:44.121-05:00  INFO 35811 --- [async-demo] [  virtual-task-1] c.e.asyncdemo.job.AsyncDemoService       : Processing Job in PROCESSING Status on Iteration 10. VT: true
```

Sample Logging Output for Job Update:
```text
2024-07-10T10:42:39.260-05:00  INFO 35811 --- [async-demo] [tomcat-handler-3] c.example.asyncdemo.job.AsyncController  : Starting Job Update
2024-07-10T10:42:39.260-05:00  INFO 35811 --- [async-demo] [tomcat-handler-3] c.e.asyncdemo.job.AsyncDemoService       : Starting Update for job 1
2024-07-10T10:42:39.260-05:00  INFO 35811 --- [async-demo] [tomcat-handler-3] c.e.a.stream.AsyncDemoStreamProducer     : Sending Job 1
2024-07-10T10:42:39.270-05:00  INFO 35811 --- [async-demo] [tomcat-handler-3] c.e.asyncdemo.job.AsyncDemoService       : Completing Update for Job 1
2024-07-10T10:42:39.270-05:00  INFO 35811 --- [async-demo] [tomcat-handler-3] c.example.asyncdemo.job.AsyncController  : Completed Job Update. VT true
2024-07-10T10:42:39.283-05:00  INFO 35811 --- [async-demo] [ rabbit-simple-0] c.e.a.stream.AsyncDemoStreamConsumer     : Stream starting for job 1
2024-07-10T10:42:39.284-05:00  INFO 35811 --- [async-demo] [ rabbit-simple-0] c.e.asyncdemo.job.AsyncDemoService       : Processing Job in PROCESSING Status on Iteration 1. VT: true
2024-07-10T10:42:39.296-05:00  INFO 35811 --- [async-demo] [ rabbit-simple-0] c.e.asyncdemo.job.AsyncDemoService       : Processing Job in PROCESSING Status on Iteration 10. VT: true
2024-07-10T10:42:39.297-05:00  INFO 35811 --- [async-demo] [ rabbit-simple-0] c.e.a.stream.AsyncDemoStreamConsumer     : Stream Completing for job 1. VT: true
```