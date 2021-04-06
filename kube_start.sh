#!/bin/bash
## Configure minikube machine
# Clean minikube:
minikube delete;
# Start minikube:
    # macOS:
minikube start --vm=true --driver=hyperkit;
    # Other:
#minikube start;
# Assign context:
kubectl config use-context minikube;
# Enable ingress to get services outside kube cluster:
minikube addons enable ingress;

## Build
# Allow local docker images
eval $(minikube docker-env);
# Build sources and create docker images
mvn clean package;

## Run
# Run db:
kubectl apply -f kube/db;
# Wait :)
sleep 1m;
# Run all other services:
kubectl apply -f kube;