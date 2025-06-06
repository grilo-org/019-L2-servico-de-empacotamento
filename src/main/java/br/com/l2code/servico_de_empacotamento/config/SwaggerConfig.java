package br.com.l2code.servico_de_empacotamento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Empacotamento de Produtos")
                        .description("API para gerenciamento de caixas e empacotamento de produtos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("L2 Code")
                                .email("contato@l2code.com.br")
                                .url("https://l2code.com.br"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
