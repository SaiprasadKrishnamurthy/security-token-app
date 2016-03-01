/* Copyright (c) 2016 Nouveautech Limited. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Nouveautech Limited ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Nouveautech Limited.
 *
 * NOUVEAUTECH LIMITED MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. NOUVEAUTECH LIMITED SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/

package com.micro.security;

import com.google.common.base.Predicates;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application main.
 *
 * @author saikris
 */
@SpringBootApplication
@Configuration
@EnableConfigurationProperties
@EnableAsync
@ComponentScan("com.micro.security")
@EnableSwagger2
public class SecurityTokenApp {

    /**
     * Main method.
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        SpringApplicationBuilder application = new SpringApplicationBuilder();
        application //
                .addCommandLineProperties(true) //
                .sources(SecurityTokenApp.class) //
                .showBanner(true)
                .main(SecurityTokenApp.class) //
                .registerShutdownHook(true)
                .run(args);
    }

    /**
     * Swagger 2 docket bean configuration.
     *
     * @return swagger 2 Docket.
     */
    @Bean
    public Docket resolverApi() throws Exception{
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("security-app")
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude Spring error controllers
                .build();
    }

    private ApiInfo apiInfo() throws Exception{
        return new ApiInfoBuilder()
                .title("Security Gateway APP")
                .description(IOUtils.toString(SecurityTokenApp.class.getClassLoader().getResourceAsStream("description.html")))
                .contact("Saiprasad.Krishnamurthy@gmail.com, saiprkri@cisco.com")
                .license("Apache V 2.0")
                .licenseUrl("https://github.com/SaiprasadKrishnamurthy")
                .version("1.0")
                .termsOfServiceUrl("https://github.com/SaiprasadKrishnamurthy")
                .build();
    }
}
