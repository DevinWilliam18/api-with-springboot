package com.example.demo;

import java.util.Arrays;
import java.util.Iterator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.server.header.CacheControlServerHttpHeadersWriter;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

@SpringBootApplication
public class DemoApplication{

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		System.out.println("Type of an object: " + applicationContext.getClass().getName());
	}

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
   @Bean
   public ApplicationRunner initData(RoleRepository roleRepository) {
       return new ApplicationRunner() {
           @Override
           public void run(ApplicationArguments args) throws Exception {
       		
           	try {
					Role role = new Role();
					role.setName("ROLE_ADMIN");
					roleRepository.save(role);
					
					System.out.println("Initial records loaded successfully.");
				
           	
           	} catch (Exception e) {
					e.printStackTrace();
				}
           }
       };
   }
    
//    @Bean
//    public ApplicationRunner getBeans(ApplicationContext applicationContext) {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//        		String[] getBeanStrings = applicationContext.getBeanDefinitionNames();
//        		
//        		for (String string : getBeanStrings) {
//        			System.out.println(string);
//        		}
//        		
//            }
//        };
//    }
    
}
