package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userServiceImpl;
    private SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userServiceImpl) {
        this.successUserHandler = successUserHandler;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()                // делаем страницу регистрации недоступной для авторизированных пользователей
                .antMatchers("/", "/index").permitAll()// доступность всем
                // защищенные URL
                .antMatchers("/admin/**").hasRole("ADMIN")  // разрешаем входить на /admin пользователям с ролью admin
                .antMatchers("/vip/**").hasAnyRole("ADMIN", "VIP")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER", "VIP")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)//указываем логику обработки при логине
                .permitAll();
                http
                        .logout()
                        .logoutSuccessUrl("/login") // указываем URL, на который перейти после выхода
                        .invalidateHttpSession(true); // инвалидация сессии после выхода
    }


    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); // Необходимо для шифрования паролей
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userServiceImpl);
        return authenticationProvider;
    }
}