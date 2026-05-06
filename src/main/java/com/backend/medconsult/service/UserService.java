package com.backend.medconsult.service;

import com.backend.medconsult.dto.AuthResponseDto;
import com.backend.medconsult.dto.UserLoginDto;
import com.backend.medconsult.dto.UserRegisterDto;

public interface UserService {

    AuthResponseDto register(UserRegisterDto dto);

    AuthResponseDto login(UserLoginDto dto);

}
