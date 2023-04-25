package gettingstarted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
@EnableWebSecurity
public class SpringSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().
				withUser(User.withDefaultPasswordEncoder().username("paul").password("warren").roles("READER", "AUTHOR")).
				withUser(User.withDefaultPasswordEncoder().username("eric").password("wimp").roles("READER").
						build());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
//				.requestMatchers(new AntPathRequestMatcher("/files/**","GET")).hasRole("READER")
//				.requestMatchers(new AntPathRequestMatcher("/files/**","PUT")).hasRole("AUTHOR")
//				.requestMatchers(new AntPathRequestMatcher("/files","POST")).hasRole("AUTHOR")
//				.requestMatchers(new AntPathRequestMatcher("/files/**/content","GET")).hasRole("READER")
//				.requestMatchers(new AntPathRequestMatcher("/files/**/content","PUT")).hasRole("AUTHOR")
				.anyRequest().permitAll()
				.and().httpBasic()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}
}