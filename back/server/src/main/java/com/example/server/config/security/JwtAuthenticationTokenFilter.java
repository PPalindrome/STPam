package com.example.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt登录授权过滤器
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    //在yml中有tokenhead和tokenheader的说明
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);
        //存在token且请求头是tokenHead
        if(null!=authHeader&&authHeader.startsWith(tokenHead)){
            //截出请求头
            String authToken = authHeader.substring(tokenHead.length());
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            //能拿到用户名，但拿不到全局对象，说明没登录
            if(null!=userName&&null== SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                //验证token是否有效，重新设置用户对象(不重新设置的话下次还拿不到，造成死循环)
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
