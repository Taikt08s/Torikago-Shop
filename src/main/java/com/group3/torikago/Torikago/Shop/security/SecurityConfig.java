package com.group3.torikago.Torikago.Shop.security;

import com.group3.torikago.Torikago.Shop.oauth.CustomOauth2User;
import com.group3.torikago.Torikago.Shop.oauth.CustomOauth2UserService;
import com.group3.torikago.Torikago.Shop.oauth.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private CustomUserDetailsService userDetailsService;
    private DataSource dataSource;
    private Oauth2LoginSuccessHandler oauth2LoginSucessHandler;
    private CustomOauth2UserService customOauth2UserService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, DataSource dataSource, Oauth2LoginSuccessHandler oauth2LoginSucessHandler, CustomOauth2UserService customOauth2UserService) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.oauth2LoginSucessHandler = oauth2LoginSucessHandler;
        this.customOauth2UserService = customOauth2UserService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // luu y khi muon test thi disable csrf!! .csrf().disable()
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/reset_password", "/login", "/forgot_password", "/verify", "/"
                                , "/torikago/**", "/register", "/register/**", "/css/**", "/js/**", "/vendor/**",
                                "/scss/**", "/403", "/product-images/**","/oauth2/**").permitAll()
                        .requestMatchers("/admin", "/admin/product-table",
                                "/admin/users-table", "/admin/product-table/bird-cage/add").hasAnyAuthority("ADMIN")
                        .requestMatchers("/manager").hasAnyAuthority("MANAGER").anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/torikago")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint().userService(customOauth2UserService).and()
                        .successHandler(oauth2LoginSucessHandler))
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedPage("/403"))
                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer.tokenRepository(persistentTokenRepository()));
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
