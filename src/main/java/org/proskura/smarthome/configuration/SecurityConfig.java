package org.proskura.smarthome.configuration;

import org.proskura.smarthome.security.jwt.JwtAuthFilter;
import org.proskura.smarthome.security.jwt.JwtAuthProvider;
import org.proskura.smarthome.security.token.TokenAuthFilter;
import org.proskura.smarthome.security.token.TokenAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//отключает конфигурирование из коробки
//Мы наследуем наш класс конфигурации от WebSecurityConfigurerAdapter, чтобы не нужно было конфигурировать все
//а только конфигурировать (переписывать) те настройки, которые нам нужны
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired TokenAuthProvider tokenAuthProvider;
    @Autowired
    JwtAuthProvider jwtAuthProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //добавляем наш кастомный фильтр перед всеми спринговыми секьюрными фильтрами
                .addFilterBefore(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login", "/device").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(tokenAuthProvider, jwtAuthProvider));
    }


    //Создаем фильтр как отдельный бин, чтобы он присуствовал с Спринг контексте.
    // Чтобы в дальнейшем можно было с ним работать и кастомизировать
    @Bean
    public TokenAuthFilter tokenAuthFilter () throws Exception {
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(authenticationManager());
        tokenAuthFilter.setAuthenticationManager(authenticationManager());
        tokenAuthFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {});
        return tokenAuthFilter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter () {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(authenticationManager());
        jwtAuthFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {});
        return jwtAuthFilter;
    }

}
