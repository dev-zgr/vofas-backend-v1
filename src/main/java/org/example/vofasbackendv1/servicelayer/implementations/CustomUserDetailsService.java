package org.example.vofasbackendv1.servicelayer.implementations;

import org.example.vofasbackendv1.data_layer.entities.UserEntity;
import org.example.vofasbackendv1.data_layer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findUserEntitiesByEmail(email);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userEntity.getRoleEnum().name()));
            return new User(userEntity.getEmail(), user.get().getPassword(), authorities);
        }else{
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
