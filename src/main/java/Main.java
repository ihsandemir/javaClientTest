import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

public class Main {
    public static void main(String[] args) {
        final HazelcastInstance client = HazelcastClient.newHazelcastClient();
        final ITopic<Object> topic = client.getReliableTopic("mytopic");

        topic.addMessageListener(new MessageListener<Object>() {
            public void onMessage(Message<Object> message) {
                System.out.println("Java client received message " + message.getMessageObject());
            }
        });

        int counter = 0;
        while (true) {
            topic.publish("Message from Java client " + (counter++));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
