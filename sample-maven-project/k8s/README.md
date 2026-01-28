# Local kind usage

## Create a local cluster

```sh
kind create cluster --name sample-maven-project
```

## Verify local cluster exists and is reachable
```sh
kubectl cluster-info --context kind-sample-maven-project
```


## Apply manifests

```sh
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/redis-pod.yaml -f k8s/redis-service.yaml
kubectl apply -f k8s/app-pod.yaml -f k8s/app-service.yaml
```

## Verify

```sh
kubectl -n sample-maven-project get pods
kubectl -n sample-maven-project port-forward svc/sample-maven-project 8080:8080
```

In another terminal:

```sh
curl -f http://localhost:8080/actuator/health
curl -f http://localhost:8080/ping
```
