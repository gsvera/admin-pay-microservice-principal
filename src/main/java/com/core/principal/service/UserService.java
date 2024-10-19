package com.core.principal.service;

import com.core.principal.dto.LoginRequestDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.dto.ResponseLoginDTO;
import com.core.principal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.core.principal.dto.UserDTO;
import com.core.principal.entity.User;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseDTO _SaveUser(UserDTO userDTO) {
        Optional<User> user = this._FindUserDuplicateByEmail(userDTO.getEmail());

        if(user.isPresent()) {
            return ResponseDTO.builder().message("Ya existe un usuario con esos datos").error(true).build();
        }

        UUID uuid = UUID.randomUUID();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDTO.getPassword());
        userDTO.setId(uuid.toString());
        userDTO.setPassword(encodedPassword);

        User newUser = new User(userDTO);

        userRepository.save(newUser);

        String token = jwtService.GetToken(newUser);
        userRepository.updateTokenById(token, newUser.getId());

        return ResponseDTO
                .builder()
                .items(
                    ResponseLoginDTO
                        .builder()
                        .token(token)
                        .idUser(newUser.getId())
                        .build()
                ).build();
    }
    public ResponseDTO _LoginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getUsername()).orElseThrow();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(user != null) {
            boolean passwordMatch = encoder.matches(loginRequestDTO.getPassword(), user.getPassword());
            if(passwordMatch) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
                String token = jwtService.GetToken(user);
                int update = userRepository.updateTokenById(user.getId(), token);
                if(update == 1) {
                    UserDTO userDTO = new UserDTO(user);
                    ResponseLoginDTO response = new ResponseLoginDTO();
                    response.setToken(token);
                    response.setDataUser(userDTO);
                    return ResponseDTO.builder().items(response).build();
                }
                return ResponseDTO.builder().error(true).message("No se pudo cerrar la sesion").build();
            }
        }
        return ResponseDTO.builder().error(true).message("Error de contraseña").build();
    }
    private Optional<User> _FindUserDuplicateByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseDTO _Logout(String token) {
        userRepository.updateToken(token);
        return ResponseDTO.builder().error(false).build();
    }
}
