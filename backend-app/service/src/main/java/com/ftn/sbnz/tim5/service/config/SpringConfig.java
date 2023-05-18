package com.ftn.sbnz.tim5.service.config;

import com.ftn.sbnz.tim5.service.security.JwtAuthenticationFilter;
import com.ftn.sbnz.tim5.service.services.implementation.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Configuration
////@EnableMethodSecurity
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//@RequiredArgsConstructor
//public class SpringConfig {
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests()
//                .antMatchers("/auth/**", "/users/register", "/users/activate-account", "/verify/**", "/ws/**")
//                .permitAll().anyRequest().authenticated()
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .httpBasic()
//                .and()
//                .cors().and().csrf().disable()
//                .exceptionHandling();
//
//       return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/auth/login");
//    }
//
//}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    public SpringConfig(CustomUserDetailsService customUserDetailsService, UserService userService) {
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService))
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**")
                .permitAll()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/cash-credit/proba")
                .permitAll()
                .antMatchers("/clients/**")
                .permitAll()
                .antMatchers("/users/{email}")
                .permitAll()
                .antMatchers("/users/activate-account")
                .permitAll()
                .antMatchers("/account-types/all-types")
                .permitAll()
                .antMatchers("/employers/all-employers")
                .permitAll()
                .antMatchers("/verify/**")
                .permitAll()
                .antMatchers("/ws/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}