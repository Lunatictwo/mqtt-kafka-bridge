package mqtt;

public class MqttMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MqttHandler handler = new MqttHandler();
		handler.receiveMessageByMqtt();
		handler.publishMessageByMqtt("test_topic");
		
	}
}
