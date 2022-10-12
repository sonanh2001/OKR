package org.aibles.okr3.configuration;

import lombok.RequiredArgsConstructor;
import org.aibles.okr3.repository.KeyResultRepository;
import org.aibles.okr3.repository.ObjectiveRepository;
import org.aibles.okr3.repository.RefreshTokenRepository;
import org.aibles.okr3.repository.UserRepository;
import org.aibles.okr3.service.KeyResultService;
import org.aibles.okr3.service.ObjectiveService;
import org.aibles.okr3.service.RefreshTokenService;
import org.aibles.okr3.service.UserService;
import org.aibles.okr3.service.impl.KeyResultServiceImpl;
import org.aibles.okr3.service.impl.ObjectiveServiceImpl;
import org.aibles.okr3.service.impl.RefreshTokenServiceImpl;
import org.aibles.okr3.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories(basePackages = {"org.aibles.okr3.repository"})
@ComponentScan(basePackages = {"org.aibles.okr3.repository"})
@RequiredArgsConstructor
public class OKRsConfiguration {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectiveService objectiveService(
      ObjectiveRepository repository,
      @Lazy KeyResultService keyResultService,
      @Lazy UserService userService) {
    return new ObjectiveServiceImpl(repository, keyResultService, userService);
  }

  @Bean
  public KeyResultService keyResultService(
      KeyResultRepository repository, @Lazy ObjectiveService objectiveService) {
    return new KeyResultServiceImpl(repository, objectiveService);
  }

  @Bean
  public UserService userService(
      UserRepository repository,
      PasswordEncoder passwordEncoder,
      @Lazy ObjectiveService objectiveService,
      @Lazy RefreshTokenService refreshTokenService) {
    return new UserServiceImpl(repository, passwordEncoder, objectiveService, refreshTokenService);
  }

  @Bean
  public RefreshTokenService refreshTokenService(
      RefreshTokenRepository repository, @Lazy UserService userService) {
    return new RefreshTokenServiceImpl(repository, userService);
  }
}
