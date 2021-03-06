package caojx.learn.simple;

import caojx.learn.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息 新的接收消息方式
 */
public class ReciverNew {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();

        //2.创建频道
        Channel channel = connection.createChannel();

        //3.队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //4.接收消息
       DefaultConsumer consumer =  new DefaultConsumer(channel) {
           //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body, "utf-8");
                System.out.println("new api reciver:"+msgString);
            }
        };

       //5.监听队列
       channel.basicConsume(QUEUE_NAME, true, consumer);



    }
}
