package io.getarrays.contactapi.security;

import io.getarrays.contactapi.user.CustomUser;
import io.getarrays.contactapi.user.User;
import io.getarrays.contactapi.user.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("request Email :" + email);
        System.out.println("loadUserByUsername start");

        // 사용자 조회, 없으면 예외 발생
        User user = userRepo.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getUserEmail().contains("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if(user.getUserEmail().contains("manager")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 사용자가 있다면 UserDetails 객체 생성
        CustomUser customUser = new CustomUser(user.getUserEmail(), user.getPassword(), authorities);
        customUser.setDisplayName(user.getUserName());
        customUser.setUserId(user.getId());
        return customUser;
    }
}
