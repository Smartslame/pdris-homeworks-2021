mvn clean package -f ..;
docker push smartslame/currency-service:hw4;
docker push smartslame/weather-service:hw4;
docker push smartslame/prediction-service:hw4;
docker push smartslame/eureka:hw4;