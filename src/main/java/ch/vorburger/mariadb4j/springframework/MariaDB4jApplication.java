/*
 * Copyright (c) 2014 Michael Vorburger
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */
package ch.vorburger.mariadb4j.springframework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.vorburger.mariadb4j.MariaDB4jService;

/**
 * Spring Boot based MariaDB4j main() "Application" launcher.
 * 
 * @see MariaDB4jService
 * 
 * @author Michael Vorburger
 */
@Configuration
@EnableAutoConfiguration
public class MariaDB4jApplication implements ExitCodeGenerator {

    @Bean
    protected MariaDB4jSpringService mariaDB4j() {
        MariaDB4jSpringService bean = new MariaDB4jSpringService();
        return bean;
    }
	
	protected @Autowired MariaDB4jSpringService dbService;
	
	public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(MariaDB4jApplication.class);
        app.setShowBanner(false);
        ConfigurableApplicationContext ctx = app.run(args);
        
        MariaDB4jService.waitForKeyPressToCleanlyExit();
        
        ctx.stop();
        ctx.close();
	}

	@Override
	public int getExitCode() {
		return dbService.lastException == null ? 0 : -1;
	}
	
}