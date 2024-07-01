package br.uff.ic.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf->csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/autenticacao/registrar").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/autenticacao/papeis/**").hasRole("ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/organizador").hasRole("ORGANIZER")
                                .requestMatchers("/user/").hasRole("USER")

                                //Autorizações da Controller Edicao
                                .requestMatchers(HttpMethod.POST, "/edicao/cadastrar").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/edicao/cadastrar_organizador/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/edicao/ler_edicao_de_evento/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/edicao/solicitar_dados_categoria/**").hasRole("ORGANIZER")
                                .requestMatchers(HttpMethod.PUT, "/edicao/editar/**").hasRole("ORGANIZER")
                                .requestMatchers(HttpMethod.GET, "/edicao/ler/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/edicao/remover/**").hasRole("ADMIN")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
