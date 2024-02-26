package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.UserDto;
import by.kirilldikun.onlinestoreapi.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
