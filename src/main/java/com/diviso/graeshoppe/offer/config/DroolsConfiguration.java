package com.diviso.graeshoppe.offer.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {
	
	 private final Logger log = LoggerFactory.getLogger(DroolsConfiguration.class);
	 
	 private static final String drlFile = "Offerrule.drl";
	 KieServices kieServices;
	 
	public DroolsConfiguration() {
		super();
		 kieServices = KieServices.Factory.get();
		 KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		 kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile)); 
		 KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
	     kieBuilder.buildAll();   
	     KieContainer kieContainer= kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
	
	}

	@Bean(name = "kieContainer")
	public KieContainer kieContainer(){
		
		return kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		
	}
	

}
