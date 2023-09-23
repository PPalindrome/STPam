package com.example.server.config.security;

import com.example.server.pojo.Admin;
import com.example.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Override
    protected void  configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers(
                "/login",
                "/register",
                "/files/**",
                "/build",
                "/download",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/data/**",
                "/encrypt",
                "/queryDatasets/**",
                "/queryModels",
                "/queryProduct",
                "/save",
                "/getRole",
                "/directoryStructure/**",
                "/fileEdit/**/**",
                "/getImgBytes"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //使用jwt，不需要csrf
        http.csrf()
                .disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .authorizeRequests()
                //请求都要认证
                .anyRequest()
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();
        //jwt的登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Admin admin=adminService.getAdminByUserName(username);
            if(null!=admin){
                return admin;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 防止模型文件修改结果被防火墙拦下
     * @return
     */
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }
}
