package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Ship(@Payload Ordered ordered){

        if(ordered.isMe())
        {
            //System.out.println("##### listener Ship : " + ordered.toJson());
            //1. 실제 biz로직 수행하면 됨
            if(ordered.isMe())
            {
                Delivery delivery = new Delivery();
                delivery.setOrderId(ordered.getId());
                delivery.setStatus("SHIPPED");

                deliveryRepository.save(delivery);
                //POST 이벤트가 발생하는 것임... delivery 어그리게이트에서 이벤트 기다리다 처리할거임...
                // bean 간의 오토와이어들 주입이 있어야 함,,, 위에 소스 추가함
            }
        }
    }

}
