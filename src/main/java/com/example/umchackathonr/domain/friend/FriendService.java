package com.example.umchackathonr.domain.friend;


import com.example.umchackathonr.domain.friend.dto.FriendRequest;
import com.example.umchackathonr.domain.user.User;
import com.example.umchackathonr.domain.user.UserRepository;
import com.example.umchackathonr.exception.errorCode.UserErrorCode;
import com.example.umchackathonr.exception.exception.RestApiException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.amazonaws.services.ec2.model.PrincipalType.User;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 생성
    public Long save(Long userId, FriendRequest request) {
        String name = request.getName();
        LocalDate birthday = request.getBirthday();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));

        if (friendRepository.existsByNameAndBirthday(name, birthday)) {
            throw new RestApiException(UserErrorCode.DUPLICATION_FRIEND);
        }

        Friend friend = Friend.builder()
                .user(user)
                .name(name)
                .birthday(birthday)
                .build();

        Friend savedFriend = friendRepository.save(friend);

        return savedFriend.getId();
    }

    // 친구 수정
    public void update(Long userId, FriendRequest request, Long friendId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));

        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_FRIEND));

        friend.updateFriendInformation(request.getName(), request.getBirthday());
        friendRepository.save(friend);
    }

    // 친구 삭제
    public void delete(Long userId, Long friendId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));

        friendRepository.findById(friendId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_FRIEND));

        friendRepository.deleteById(friendId);
    }
}
