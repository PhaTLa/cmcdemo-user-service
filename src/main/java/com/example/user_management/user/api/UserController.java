package com.example.user_management.user.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user_management.user.dao.UserMapper;
import com.example.user_management.user.dto.req.ReqLogin;
import com.example.user_management.user.dto.req.ReqUserDto;
import com.example.user_management.user.dto.resp.UserIdAndNameRespDto;
import com.example.user_management.user.entity.User;
import com.example.user_management.user.service.AppUserService;
import com.example.user_management.user.service.UserService;
import com.example.user_management.user.util.JwtUtil;
import com.example.user_management.exception.CustomExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class UserController {
    private final AppUserService appUserService;
    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    HttpSession httpSession;

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Validated @RequestBody ReqUserDto dto) {
        return new ResponseEntity<>(userService.insert(dto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ReqLogin dto, HttpServletRequest request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        com.example.user_management.user.model.User user = userMapper.selectByEmail(dto.getUsername());
        user.setPassword(null);
        request.getSession().setAttribute("userId", user.getId());
        JwtUtil.successfulAuthentication(httpServletRequest, httpServletResponse, authentication, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/get-user/{userId}")
    public ResponseEntity<?> getUsers(@PathVariable(name = "userId") Long id){
        return ResponseEntity.ok().body(userService.selectByPrimaryKey(id));
    }
    @GetMapping("/get-id/{username}")
    public ResponseEntity<?> getIdByUserName(@PathVariable("username") String username){
        Long idUser = userMapper.getUserIdByUserName(username);
        if(idUser == null){
            return ResponseEntity.notFound().build();
        }
        UserIdAndNameRespDto respDto = new UserIdAndNameRespDto(idUser,username);
        return ResponseEntity.ok().body(respDto);
    }
//
//    @PostMapping("/role/save")
//    public ResponseEntity<Role> saveRole(@RequestBody Role role){
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveRole(role));
//    }
//
//    @PostMapping("/role/addtouser")
//    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
//        userService.addRoleToUser(form.getUsername(), form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = appUserService.getUser(username);
                List<String> roles = new ArrayList<>();
                roles.add(user.getRole().getName());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", roles)
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e) {
                log.error("Error refresh token in: {}", e.getMessage());
                CustomExceptionHandler<Exception> handler = new CustomExceptionHandler<>();
                handler.exceptionHandler(e, request, response);
            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
