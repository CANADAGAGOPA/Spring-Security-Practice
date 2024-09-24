package com.example.TestSecurity.application;

import com.example.TestSecurity.domain.UserEntity;
import com.example.TestSecurity.dto.JoinDTO;
import com.example.TestSecurity.infrastructure.UserRepository;
import com.example.TestSecurity.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor // 생성자 주입
@Transactional(readOnly = true) // 읽기 전용
public class JoinServiceImpl implements JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional // 데이터를 추가 및 변경을 하기에 읽기와 쓰기가 모두 가능한 상태
    public void joinProcess(JoinDTO joinDTO) {

        // db 에 이미 존재하는 유저인지 확인
        boolean userExists = userRepository.findByUsername(joinDTO.getUsername()).isPresent();

        if (userExists) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // 유저 정보 저장
        UserEntity userEntity = UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_" + RoleType.USER.name())
                .build();

        userRepository.save(userEntity);
    }
}