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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()                // делаем страницу регистрации недоступной для авторизированных пользователей

                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").permitAll()
                .and()
                .formLogin().successHandler(successUserHandler)//указываем логику обработки при логине
                .permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher((new AntPathRequestMatcher("/logout")))
                .logoutSuccessUrl("/login")
                .and().csrf().disable();
    }


        @Bean
        public PasswordEncoder passwordEncoder () {
            return new BCryptPasswordEncoder(); // Необходимо для шифрования паролей
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider () {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            authenticationProvider.setUserDetailsService(userService);
            return authenticationProvider;
        }
    }