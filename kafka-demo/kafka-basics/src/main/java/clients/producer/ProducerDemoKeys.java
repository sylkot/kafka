package clients.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());

    public static void main(String[] args) {

        log.info("I am Kafka Producer!");

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "id_123", "hello world");

        producer.send(producerRecord, new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info("Received new metadata:\n" + 
                            "Topic: " + metadata.topic() + "\n" + 
                            "Key: " + producerRecord.key() + "\n" +
                            "Partition" + metadata.partition() + "\n" + 
                            "Offset" + metadata.offset() + "\n" + 
                            "Timestamp" + metadata.timestamp() + "\n");
                } else {
                    log.error("Error while producing: ", exception);
                }

            }
        });

        producer.close();
    }

}
