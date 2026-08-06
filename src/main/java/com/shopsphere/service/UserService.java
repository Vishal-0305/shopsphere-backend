package com.shopsphere.service;

import com.shopsphere.dto.RegisterRequest;
import com.shopsphere.entity.User;

public interface UserService {

    User registerUser(RegisterRequest request);
}
