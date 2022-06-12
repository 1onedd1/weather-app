## Description
This application shows a weather in the city.

It has two modules - publisher and subscriber.
- Publisher - the module getting data about weather using API by OpenWeatherMap and sends weather parameters to the MQTT broker
- Subscriber - the module receiving data from MQTT

Both modules work with industrial network protocol MQTTV3.

Update data once every five seconds.

## User interface
| Publisher                                                                                        | Subscriber                                                                                        |
|--------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| <img align="left" width="350" height="290" src="./assets/image/InterfacePublisher.png?raw=true"> | <img align="left" width="320" height="290" src="./assets/image/InterfaceSubscriber.png?raw=true"> |

Publisher has settings where you can change the city and units of measure.

| Settings                                                                                        |
|-------------------------------------------------------------------------------------------------|
| <img align="left" width="350" height="290" src="./assets/image/InterfaceSettings.png?raw=true"> |
## Dependencies
- [MQTTV3](https://mvnrepository.com/artifact/org.eclipse.paho/org.eclipse.paho.client.mqttv3) - network protocol
- [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson) - Json parser


