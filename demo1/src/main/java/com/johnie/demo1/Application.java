package com.johnie.demo1;

import com.johnie.demo1.common.Foo2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class Application {
    private final Logger logger = LoggerFactory.getLogger(Application.class);

    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();
    private final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @KafkaListener(id = "fooGroup", topics = "topic1")
    public void listen(Foo2 foo) {
        logger.info("Received: " + foo);
        if (foo.getFoo().startsWith("fail")) {
            throw new RuntimeException("failed");
        }
        this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
    }

    @KafkaListener(id = "dltGroup", topics = "topic1.DLT")
    public void dltListen(byte[] in) {
        logger.info("Received from DLT: " + new String(in));
        this.exec.execute(() -> System.out.println("Hit Enter to terminate..."));
    }

}
