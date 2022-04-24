package co.gov.dane;
 
 import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
 import com.mangofactory.swagger.models.dto.ApiInfo;
 import com.mangofactory.swagger.plugin.EnableSwagger;
 import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 
 @Configuration
 @EnableSwagger
 public class SwaggerConfig
 {
   private SpringSwaggerConfig springSwaggerConfig;
   
   @org.springframework.beans.factory.annotation.Autowired
   public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
   {
     this.springSwaggerConfig = springSwaggerConfig;
   }
   
   @Bean
   public SwaggerSpringMvcPlugin customImplementation()
   {
     		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(new String[] { "/dane/*/.*" });
   }
   
   private ApiInfo apiInfo() {
     ApiInfo apiInfo = new ApiInfo("Dane API", "API for Dane", "Dane API terms of service", "admin@admin.com", "Dane API Licence Type", "Dane API License URL"); 
     return apiInfo;
   }
 }
