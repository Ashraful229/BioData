package com.product.acl.auth;

import com.product.acl.role.Role;
import com.product.acl.role.RoleRepository;
import com.product.acl.security.jwt.config.JwtUtils;
import com.product.acl.security.jwt.payload.request.LoginRequest;
import com.product.acl.security.jwt.payload.request.SignupRequest;
import com.product.acl.security.jwt.payload.response.JwtResponse;
import com.product.acl.security.service.UserDetailsImpl;
import com.product.acl.user.User;
import com.product.acl.user.UserRepository;
import com.product.acl.user.UserService;
import helper.exception.ResourceNotFoundException;
import helper.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private RoleRepository repository;
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @PostMapping("/acl/register")
    public ResponseEntity<?> register (@Valid @RequestBody SignupRequest signUpRequest) throws ResourceNotFoundException {

        System.out.println("ss=="+signUpRequest);
        User user = new User(
                signUpRequest.getUsername(),
                this.bCryptPasswordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone()

        );
        Set<String> strRoles=signUpRequest.getRole();
        Set<Role> roles=new HashSet<>();

        if(strRoles==null|| strRoles.isEmpty())
        {
            Role userRole=this.repository.getRoleByAuthority(("ROLE_USER"));
            if (userRole==null)
            {
              throw new ResourceNotFoundException("Role not set");

            }
            roles.add(userRole);
        }
        user.setRoles(roles);
        return this.userService.createUser(user);
        //return null;
    }

    @PostMapping("/acl/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.getByUsername(loginRequest.getUsername());

            String token = jwtUtils.generateJwtToken(authentication,user);
            long timeout = jwtUtils.getJwtExpirationMs();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());


            return ResponseEntity.ok(new JwtResponse(true, optionalUser.orElse(null), token, "Bearer", timeout));

        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new BaseResponse(false, "Username or password is incorrect",401));
        }


    }

}
