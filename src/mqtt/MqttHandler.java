package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHandler {

	public static String MQTT_BROKER = "tcp://xxx.xxx.xxx.xxx:1883";
	public static String MQTT_USERNAME = "admin";
	public static String MQTT_PASSWORD = "password";
	public static String MQTT_PUB_TOPIC = "BS_DOWN_TOPIC";
	public static String MQTT_SUB_TOPIC = "BS_UP_TOPIC";

	public static String caFilePath = "G:\\openssl_ca\\ca.crt";
	public static String clientCrtFilePath = "G:\\openssl_ca\\client.crt";
	public static String clientKeyFilePath = "G:\\openssl_ca\\client.key";

	/**
	 * Publish messages
	 * 
	 * @param topic
	 * @throws Exception
	 */
	public void publishMessageByMqtt(String topic) throws Exception {
		String content = "It is a test message from mqtt.";
		int qos = 2;
		String clientId = "";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttClient sampleClient = new MqttClient(MQTT_BROKER, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(MQTT_USERNAME);
			connOpts.setPassword(MQTT_PASSWORD.toCharArray());
			connOpts.setConnectionTimeout(1000);
			connOpts.setKeepAliveInterval(2000);
			/*
			 * connOpts.setSocketFactory( SslUtil.getSocketFactory(caFilePath,
			 * clientCrtFilePath, clientKeyFilePath, "cs123456"));
			 */
			try {
				sampleClient.connect(connOpts);
				MqttMessage message = new MqttMessage(content.getBytes());
				message.setQos(qos);
				sampleClient.publish(topic, message);
				System.out.println("Message published");
			} catch (Throwable e) {
				System.out.println("Error " + e.getMessage());
			}
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}

	}

	/**
	 * Receive messages
	 * 
	 * @return
	 * @throws Exception
	 */
	public void receiveMessageByMqtt() throws Exception {
		String clientId = "";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttClient sampleClient = new MqttClient(MQTT_BROKER, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(MQTT_USERNAME);
			connOpts.setPassword(MQTT_PASSWORD.toCharArray());
			connOpts.setConnectionTimeout(1000);
			connOpts.setKeepAliveInterval(2000);
			/*
			 * connOpts.setSocketFactory( SslUtil.getSocketFactory(caFilePath,
			 * clientCrtFilePath, clientKeyFilePath, "cs123456"));
			 */
			System.out.println("run receive...");
			sampleClient.setCallback(new PushCallback());
			MqttTopic mtopic = sampleClient.getTopic("test_topic");
			connOpts.setWill(mtopic, "close".getBytes(), 0, true);
			sampleClient.connect(connOpts);
			int[] Qos = { 1 };
			String[] topic1 = { "test_topic" };
			sampleClient.subscribe(topic1, Qos);
		} catch (MqttException me) {
		}

	}

}
