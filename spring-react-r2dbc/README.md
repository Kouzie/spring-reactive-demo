### 대시보드 API

```shell
curl --location 'http://localhost:8080/stock/dashboard'

curl --location 'http://localhost:8080/stock/dashboard?page=1&size=10&tag=rate&order=asc'
```

### 전체 초기화(Random)

```shell
curl --location --request POST 'http://localhost:8080/init/random'
```

### 단건 초기화(Random)

```shell
curl --location --request POST 'http://localhost:8080/init/random/42'
```

