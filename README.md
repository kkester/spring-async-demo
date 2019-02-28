# spring-async-demo

## Overview

This PoC demonstrates architecture, design, and coding strategies recommended by Pivotal. The following recommendations are implemented:

1. Spring Application Events
1. Spring Scheduler Leveraging Fixed Rate Batch and Asynchronous Processing 
1. Stream Event Triggering and Processing


## Features

- Job Processing Application
- Jobs Stored In Memory Repository
- Create Jobs
    ```
    POST /jobs HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    {
    	"status": "new"
    }
    ```
- Get Jobs to View Current Status