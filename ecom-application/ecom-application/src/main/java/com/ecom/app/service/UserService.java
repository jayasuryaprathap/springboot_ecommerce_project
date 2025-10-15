package com.ecom.app.service;

import com.ecom.app.dto.AddressDto;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.Address;
import com.ecom.app.repository.UserRepository;
import com.ecom.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private  final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){

        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){

        User user = new User();
        updateUserFromRequest(user ,userRequest);

        userRepository.save(user);
    }


    public Optional<UserResponse> fetchUser(long id){

        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id,UserRequest updateUser ){

        return userRepository.findById(id)
                .map(existingUser ->{
                    updateUserFromRequest(existingUser,updateUser);
                    userRepository.save(existingUser);
                    return true;
                }) .orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);

        }
    }


    private UserResponse mapToUserResponse(User userRequest) {

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(userRequest.getId()));
        response.setFirstName(userRequest.getFirstName());
        response.setLastName(userRequest.getLastName());
        response.setEmail(userRequest.getEmail());
        response.setPhone(userRequest.getPhone());
        response.setRole(userRequest.getRole());

        if (userRequest.getAddress() != null) {
            AddressDto addressDto= new AddressDto();
            addressDto.setStreet(userRequest.getAddress().getStreet());
            addressDto.setCity(userRequest.getAddress().getCity());
            addressDto.setState(userRequest.getAddress().getState());
            addressDto.setCountry(userRequest.getAddress().getCountry());
            addressDto.setZipcode(userRequest.getAddress().getZipcode());
            response.setAddress(addressDto);

        }
         return response;

    }


}
