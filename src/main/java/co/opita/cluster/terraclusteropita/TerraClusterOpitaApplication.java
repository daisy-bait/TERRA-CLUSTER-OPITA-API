package co.opita.cluster.terraclusteropita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TerraClusterOpitaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerraClusterOpitaApplication.class, args);
    }

}
