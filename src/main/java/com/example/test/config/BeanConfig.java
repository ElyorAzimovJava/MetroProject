package com.example.test.config;

import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.User;
import com.example.test.repository.TesterRepository;
import com.example.test.repository.UserRepository;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final UserRepository userRepository;
    private final TesterRepository testerRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return email ->{
            Optional<User> userByEmail = userRepository.findUserByEmail(email);
            if(userByEmail.isEmpty()){
               return testerRepository.findByIdNumber(email).orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("User")));
            }
            return userByEmail.get();
        } ;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(authProvider());
    }
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
    @Bean
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }
}
