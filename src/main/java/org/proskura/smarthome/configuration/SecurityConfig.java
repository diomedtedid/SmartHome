package org.proskura.smarthome.configuration;

import org.proskura.smarthome.security.CustomSecurityFilter;
import org.proskura.smarthome.sirvice.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
@EnableWebSecurity //отключает конфигурирование из коробки
//Мы наследуем наш класс конфигурации от WebSecurityConfigurerAdapter, чтобы не нужно было конфигурировать все
//а только конфигурировать (переписывать) те настройки, которые нам нужны
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired SecurityService securityService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //добавляем наш кастомный фильтр перед всеми спринговыми секьюрными фильтрами
                .addFilterAfter(getFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated();
    }

    //Cетим нашу имплементацию UserDetailsService и кодировщик паролей в AuthenticationManagerBuilder
    protected void configure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //Создаем фильтр как отдельный бин, чтобы он присуствовал с Спринг контексте.
    // Чтобы в дальнейшем можно было с ним работать и кастомизировать
    @Bean
    public CustomSecurityFilter getFilter() {
        return new CustomSecurityFilter(securityService);
    }

}
