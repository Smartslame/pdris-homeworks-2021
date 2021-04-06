## Configure minikube machine 
* Clean minikube:
``minikube delete``
* Start minikube:
    * macOS: 
``minikube start --vm=true --driver=hyperkit``
    * Other:
``minikube start``
* Assign context:
``kubectl config use-context minikube``
* Enable ingress to get services outside kube cluster:
``minikube addons enable ingress``
* You can open dashboard to see what happens with kubernetes:
``minikube dashboard``

## Build
* Allow local docker images
``eval $(minikube docker-env)``
* Build sources and create docker images
``mvn clean package``

## Run
* Run db:
``kubectl apply -f kube/db``
* Wait :)
    * You can see status in dashboard
    * Or use: ``kubectl get pod postgres``
* Run all other services:
``kubectl apply -f kube/``

## Troubleshoot
* Get your pod name:
``kubectl get pods``
* Inspet logs:
``kubectl logs <YOUR_POD_NAME>``