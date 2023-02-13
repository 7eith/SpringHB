package com.hb.bluebird.dtos;

import java.util.List;

public record UserDTO (String _username, List<String> stacks, String _message) {}