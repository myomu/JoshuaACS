package site.joshua.acs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.joshua.acs.domain.User;
import site.joshua.acs.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * @return null 로그인 실패 시 null 반환
     */
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

}
