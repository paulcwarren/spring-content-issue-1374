package gettingstarted;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class SpringContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringContentApplication.class, args);
    }
}

