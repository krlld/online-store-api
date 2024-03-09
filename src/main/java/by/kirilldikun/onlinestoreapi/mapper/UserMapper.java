package by.kirilldikun.onlinestoreapi.mapper;

import by.kirilldikun.onlinestoreapi.dto.UserDto;
import by.kirilldikun.onlinestoreapi.entity.Role;
import by.kirilldikun.onlinestoreapi.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    UserDto toUserDto(User user);

    default List<String> map(Set<Role> roles) {
        List<String> roleNames = roles
                .stream()
                .map(Role::getName)
                .toList();
        return List.copyOf(roleNames);
    }
}
