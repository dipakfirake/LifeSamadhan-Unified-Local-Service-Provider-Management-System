# Handling 1000+ Requests Per Second In LifeSamadhan

## Interview Question

**If we have a 4 vCPU and 8 GB RAM server, and more than 1000 requests per second come to our project, how can we handle it? What should we add, and where should we add it?**

## Short Answer

For 1000+ requests per second, we should not depend on a single Spring Boot server. A single 4 vCPU / 8 GB RAM server may handle a few hundred requests per second depending on API logic, database speed, and external services, but 1000+ RPS needs proper scaling.

We should use:

- Horizontal scaling with multiple Spring Boot instances.
- Load balancer or Nginx.
- Separate optimized MySQL server.
- Redis caching for read-heavy APIs.
- Async email and notification processing.
- Database indexing.
- Tomcat and HikariCP tuning.
- Load testing with JMeter or k6.

## Why One Server Is Not Enough

LifeSamadhan is not only a simple static API. It uses:

- MySQL database.
- JWT authentication.
- SMTP email for OTP.
- Razorpay payment API.
- WebSocket notifications.
- Service booking and assignment workflow.

So the bottleneck may be:

- Database connections.
- Slow SQL queries.
- SMTP delay.
- Razorpay network delay.
- CPU and memory.
- WebSocket connections.

## Architecture For 1000+ RPS

```text
React Frontend / Nginx
        |
Load Balancer
        |
--------------------------------
|              |               |
Spring Boot 1  Spring Boot 2   Spring Boot 3
        \        |             /
         \       |            /
              MySQL
                |
              Redis
                |
        Queue / Async Workers
```

Because LifeSamadhan uses JWT, it is easier to scale horizontally. JWT is stateless, so any backend instance can validate the token.

## 1. Tune Tomcat Threads

Add this in:

```text
src/main/resources/application.properties
```

```properties
server.tomcat.threads.max=300
server.tomcat.threads.min-spare=50
server.tomcat.accept-count=500
```

This allows Spring Boot embedded Tomcat to process more requests concurrently and queue extra requests.

## 2. Tune Database Connection Pool

Spring Boot uses HikariCP by default.

Add this in:

```text
src/main/resources/application.properties
```

```properties
spring.datasource.hikari.maximum-pool-size=40
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.connection-timeout=30000
```

Do not set database pool size too high blindly. Too many DB connections can overload MySQL.

## 3. Make SMTP Email Async

Currently, OTP email is sent during booking or provider acceptance. SMTP can be slow, so email should not block the main API request.

Add async support in:

```text
src/main/java/com/lifesamadhan/api/LifeSamadhanApiApplication.java
```

```java
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class LifeSamadhanApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LifeSamadhanApiApplication.class, args);
    }
}
```

Then update:

```text
src/main/java/com/lifesamadhan/api/service/EmailService.java
```

```java
@Async
public void sendOtpEmail(String toEmail, String otp, String providerName, String serviceName) {
    // existing mail sending logic
}
```

This means booking response can return quickly, while email is sent in the background.

## 4. Add Caching

Some data is read frequently and changes rarely:

- Locations.
- Service categories.
- Services.
- Provider types.

Add cache dependency in:

```text
pom.xml
```

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

Enable caching in:

```text
src/main/java/com/lifesamadhan/api/LifeSamadhanApiApplication.java
```

```java
@EnableCaching
```

Example service method:

```java
@Cacheable("locations")
public List<Location> getAllLocations() {
    return locationRepository.findAll();
}
```

For production, use Redis instead of only in-memory cache.

## 5. Add Database Indexes

For high traffic, database indexes are very important.

Add indexes on frequently searched columns:

```text
users.email
users.role
users.status
service_requests.customer_id
service_requests.provider_id
service_requests.status
service_assignments.provider_id
service_assignments.request_id
service_assignments.status
provider_locations.location_id
provider_locations.provider_id
provider_locations.status
provider_skills.provider_id
provider_skills.service_id
provider_skills.status
service_providers.user_id
service_providers.service_category_id
service_providers.verified
```

This improves:

- Login.
- Provider search.
- Customer dashboard.
- Provider dashboard.
- Admin provider filtering.

## 6. Use Queue For Heavy Work

For large traffic, use queue-based processing for:

- OTP email.
- Notifications.
- Payment post-processing.
- Audit logs.

Possible tools:

```text
RabbitMQ
Kafka
Redis Queue
```

Better booking flow:

```text
Customer books service
        |
Save request and assignment in DB
        |
Push email/notification job to queue
        |
Return API response quickly
        |
Worker sends email and notification
```

## 7. Use Load Balancer

Use Nginx, AWS ALB, Azure Load Balancer, or similar.

Example:

```text
Nginx
  -> localhost:8081
  -> localhost:8082
  -> localhost:8083
```

Since JWT is stateless, the request can go to any instance.

## 8. Optimize APIs

For high load:

- Use pagination for large lists.
- Avoid returning unnecessary entity relationships.
- Use DTOs instead of full JPA entities.
- Avoid N+1 queries.
- Keep transactions short.
- Do not call SMTP or payment APIs inside long DB transactions.

## 9. Load Testing

Use tools like:

```text
JMeter
k6
Postman Runner
Gatling
```

Check:

- Average response time.
- 95th percentile response time.
- Error rate.
- CPU usage.
- RAM usage.
- DB connection usage.
- Slow queries.
- Queue size.

## Interview-Ready Answer

If our LifeSamadhan project receives more than 1000 requests per second on a 4 vCPU and 8 GB RAM server, I would not rely on only one Spring Boot instance. I would horizontally scale the backend by running multiple Spring Boot instances behind a load balancer. Since our authentication is JWT-based and stateless, any instance can validate the token and process the request.

I would also tune Tomcat threads and HikariCP connection pool, move MySQL to a separate optimized server, add indexes on frequently used columns, make SMTP email sending asynchronous, add Redis caching for categories, services, provider types, and locations, and use a queue for heavy background tasks like email and notifications. Finally, I would test the system using JMeter or k6 and scale based on real CPU, memory, database, and response-time metrics.

## One-Line Answer

For 1000+ RPS, we handle LifeSamadhan by horizontal scaling with load balancer, stateless JWT, database optimization, Redis caching, async email/notifications, queue processing, and load testing.
