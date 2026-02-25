package ru.evendot.userservice.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.evendot.userservice.DTOs.UserDTO;
import ru.evendot.userservice.Models.User;
import ru.evendot.userservice.Exceptions.ResourceAlreadyExistsException;
import ru.evendot.userservice.Exceptions.ResourceNotFoundException;
import ru.evendot.userservice.Models.Requests.CreateUserRequest;
import ru.evendot.userservice.Models.Requests.UserUpdateRequest;
import ru.evendot.userservice.Repositories.UserRepository;
import ru.evendot.userservice.Services.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstname(req.getFirstname());
                    user.setLastname(req.getLastname());
                    user.setMiddlename(req.getMiddlename());
                    user.setPhoneNumber(req.getPhoneNumber());
                    user.setSocialMedia(req.getSocialMedia());
                    user.setAddress(req.getAddress());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceAlreadyExistsException("User already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existinguser -> {
            existinguser.setFirstname(request.getFirstname());
            existinguser.setLastname(request.getLastname());
            existinguser.setMiddlename(request.getMiddlename());
            existinguser.setAddress(request.getAddress());
            existinguser.setSocialMedia(request.getSocialMedia());
            return userRepository.save(existinguser);
        }).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User with id " + userId + " not found!");
        });
    }

    @Override
    public UserDTO convertUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
