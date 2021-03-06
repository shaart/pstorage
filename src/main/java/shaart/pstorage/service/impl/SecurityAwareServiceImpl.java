package shaart.pstorage.service.impl;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import shaart.pstorage.component.UserDataContext;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.exception.UnauthorizedException;
import shaart.pstorage.service.SecurityAwareService;
import shaart.pstorage.service.UserService;

@Component
@AllArgsConstructor
public class SecurityAwareServiceImpl implements SecurityAwareService {

  private final UserService userService;
  private final UserDataContext userDataContext;

  @Override
  public void authorize(String username, String password) {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(username, password);

    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authToken);

    final UserDto currentUser = currentUser()
        .orElseThrow(
            () -> new UnauthorizedException("Can't load user data because current user not found"));
    userDataContext.loadUserData(currentUser);
  }

  @Override
  public Optional<UserDto> currentUser() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    String username = (String) authentication.getPrincipal();

    return userService.findByName(username);
  }
}
