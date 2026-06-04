package com.backend.medconsult.service;

import com.backend.medconsult.dto.authDto.AuthResponseDto;
import com.backend.medconsult.dto.authDto.UserLoginDto;
import com.backend.medconsult.dto.authDto.UserRegisterDto;

public interface UserService {

    AuthResponseDto register(UserRegisterDto dto);

    AuthResponseDto login(UserLoginDto dto);

}
