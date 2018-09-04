package org.proskura.smarthome.configuration;

import org.proskura.smarthome.security.TokenAuthFilter;
import org.proskura.smarthome.security.TokenAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;

import java.util.Collections;

import static java.util.Arrays.asList;

@Configuration
@EnableWebSecurity //отключает конфигурирование из коробки
//Мы наследуем наш класс конфигурации от WebSecurityConfigurerAdapter, чтобы не нужно было конфигурировать все
//а только конфигурировать (переписывать) те настройки, которые нам нужны
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired TokenAuthProvider tokenAuthProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //добавляем наш кастомный фильтр перед всеми спринговыми секьюрными фильтрами
                .addFilterBefore(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(tokenAuthProvider));
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

}
