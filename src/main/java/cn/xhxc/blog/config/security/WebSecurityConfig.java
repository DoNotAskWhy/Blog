package cn.xhxc.blog.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		// 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//	        tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    	auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 如果有允许匿名的url，填在下面
				.antMatchers("/", "/user/getCheckCode", "/user/reg", 
						"/login", "/login/error","/user/showUsername",
						"/user/findByPassword","/file","/user/hello",
						"/article/getAllsArticle","/article/getAeticleLimit",
						"/article/getMaxByfabulousSize").permitAll().anyRequest()
				.authenticated().and()
				// 设置登陆页
				.formLogin().loginPage("/login")
//                .loginProcessingUrl("/user/login")
				// 设置登陆成功页
//                .successHandler(customAuthenticationSuccessHandler)
//                .failureHandler(customAuthenticationFailureHandler)
				.defaultSuccessUrl("/index.html").failureUrl("/login/error")
				.and()
				.logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
				.and()
				.rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService)
//                .and()
//				.sessionManagement().invalidSessionUrl("/login/timeout")
                ;
		System.out.println();
		http.headers().frameOptions().disable();
		// 关闭CSRF跨域
//		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		System.out.println("静态资源拦截");
		// 设置拦截忽略文件夹，可以对静态资源放行
//		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/font-awesome-4.7.0/**","/editor/**","/web/**","/upload/**","/index.html");
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/font-awesome-4.7.0/**","/editor/**","/upload/**","/index.html","/web/login.html",
				"/web/register.html","/web/findByPassword.html");
	}
}