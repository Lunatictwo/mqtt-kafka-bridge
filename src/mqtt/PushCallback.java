package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.LinkedHashMap;

public class PushCallback implements MqttCallback {

	public void connectionLost(Throwable cause) {
		System.out.println("cause:" + cause);
		MqttHandler mqtt = new MqttHandler();
		try {
			mqtt.receiveMessageByMqtt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete" + token.isComplete());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Message Arrived");
		System.out.println(message.toString());
	}
}