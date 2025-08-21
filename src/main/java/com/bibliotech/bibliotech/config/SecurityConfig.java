package com.bibliotech.bibliotech.config;

import com.bibliotech.bibliotech.util.JwtAuthFilter;
import com.bibliotech.bibliotech.service.autenticacao.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UsuarioDetailsService usuarioDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("Authorization", "Content-Type")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/existe").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/emails/enviar/senha").permitAll()

                        // -------------------------------
                        // Exemplares
                        // -------------------------------
                        // USUARIO pode listar e ver detalhes
                        .requestMatchers(HttpMethod.GET, "/exemplares", "/exemplares/{id}")
                        .hasAnyRole("USER", "ADMINISTRADOR")
                        // ADMIN pode criar, atualizar e excluir
                        .requestMatchers(HttpMethod.GET, "/exemplares/ultimo").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/exemplares").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/exemplares").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/exemplares/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/exemplares/{id}").hasRole("ADMINISTRADOR")

                        // -------------------------------
                        // Livros
                        // -------------------------------
                        // Admin pode fazer todas as requisições para livros
                        .requestMatchers("livros/**").hasRole("ADMINISTRADOR")

                        // -------------------------------
                        // Usuários
                        // -------------------------------
                        // USUARIO pode acessar seu perfil
                        .requestMatchers(HttpMethod.GET, "/usuarios/{id}").hasAnyRole("USER", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasAnyRole("USER", "ADMINISTRADOR")
                        // ADMIN pode gerenciar todos os usuários
                        .requestMatchers("/usuarios/**").hasRole("ADMINISTRADOR")

                        // -------------------------------
                        // Empréstimos
                        // -------------------------------
                        // USUARIO pode criar empréstimo e listar os seus
                                // USUARIO: listar e criar apenas os próprios empréstimos
                        .requestMatchers(HttpMethod.GET, "/emprestimos/usuario/**").hasAnyRole("USER", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/emprestimos").hasAnyRole("USER", "ADMINISTRADOR")
                        // ADMIN: gerenciar todos os empréstimos
                        .requestMatchers("/emprestimos/**").hasRole("ADMINISTRADOR")


                        // -------------------------------
                        // Categorias
                        // -------------------------------
                        .requestMatchers("/editoras/**").hasRole("ADMINISTRADOR")


                        // -------------------------------
                        // Qualquer outra requisição exige autenticação
                        // -------------------------------
                        .anyRequest().authenticated()

                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
