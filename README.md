# TokenBucket

The project presents an implementation of the token bucket algorithm with flexible configuration. 
Through a resource file, you can enable and disable request filtering, set the number of 
requests allowed and the time interval between them.

```yaml
token-bucket:
  enable: true
  tokens: 2
  refill-period-in-millis: 10000
```

## Launch instructions
1. Build project
```shell
mvn install
```

2. Run project in container
```shell
docker-compose up
```
