package com.immdream.security.config;

import com.immdream.security.filter.TokenAuthenticationFilter;
import com.immdream.security.filter.TokenLoginFilter;
import com.immdream.security.security.JwtDefaultPasswordEncoder;
import com.immdream.security.security.TokenLogoutHandler;
import com.immdream.security.security.JwtTokenManager;
import com.immdream.security.security.UnauthorizedEntryPoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Security配置类
 * <p>
 * news com.immdream.security.config
 *
 * @author immDream
 * @date 2023/04/11/22:35
 * @since 1.8
 */
// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtTokenManager jwtTokenManager;
    private JwtDefaultPasswordEncoder jwtDefaultPasswordEncoder;
    private RedisTemplate redisTemplate;

    // @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, JwtDefaultPasswordEncoder jwtDefaultPasswordEncoder,
                                  JwtTokenManager jwtTokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.jwtDefaultPasswordEncoder = jwtDefaultPasswordEncoder;
        this.jwtTokenManager = jwtTokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(jwtTokenManager,redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), jwtTokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), jwtTokenManager, redisTemplate)).httpBasic();
    }

    /**
     * 密码处理
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(jwtDefaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
}