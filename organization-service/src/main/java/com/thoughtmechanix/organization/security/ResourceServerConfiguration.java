package com.thoughtmechanix.organization.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception{
        //tutti endpoint sono accessibili solo a token validi, senza importanza quale che siano ruoli associati
    	//ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer_registry = http.authorizeRequests();  //Crea ExpressionUrlAuthorizationConfigurer e suo ExpressionInterceptUrlRegistry  ,  lo aggiunge a http.configurers, ritorna il freshly created ExpressionInterceptUrlRegistry 
    	//AuthorizedUrl authorizedUrl = configurer_registry.anyRequest(); 																	//Aggiunge AnyRequestMatcher a configurer_registry.unmappedMatchers e ritorna ExpressionUrlAuthorizationConfigurer$AuthorizedUrl  contenente AnyRequestMatcher
    	//ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = authorizedUrl.authenticated();										//Setta configurer_registry.unmappedMatchers=null ed invece setta configurer_registry.urlMappings = AbstractConfigAttributeRequestMatcherRegistry(authenticated,AnyRequestMatcher)
    	  
    	//Quindi a decidere quali request possono passare sara' alla fin fine ExpressionInterceptUrlRegistry.urlMappings!!
    	
    	
    	
         
    	 http
        .authorizeRequests()
          .antMatchers(HttpMethod.DELETE, "/v1/organizations/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated();
          
    }
}
