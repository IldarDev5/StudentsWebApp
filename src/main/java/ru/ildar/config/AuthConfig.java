package ru.ildar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.ildar.services.LoginUserDetailsService;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(loginUserDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.formLogin().
                loginPage("/loginPage").loginProcessingUrl("/login").
                defaultSuccessUrl("/startPage").failureUrl("/loginPage?auth=fail")
                .usernameParameter("username").passwordParameter("password")
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/startPage")
            .and().authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").
             antMatchers("/moderation/**")
              .access("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_FACULTY_MODERATOR')")
             .antMatchers("/stud/**")
              .access("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_FACULTY_MODERATOR','ROLE_STUDENT')");
    }
}
