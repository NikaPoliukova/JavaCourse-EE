package com.example.users.converter;

import com.example.users.dto.UserDtoRest;
import com.example.users.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public List<UserDtoRest> toDto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDtoRest> list = new ArrayList<UserDtoRest>( users.size() );
        for ( User user : users ) {
            list.add( userToUserDtoRest( user ) );
        }

        return list;
    }

    protected UserDtoRest userToUserDtoRest(User user) {
        if ( user == null ) {
            return null;
        }

        Long userId = null;
        String userName = null;
        Date createdDate = null;

        userId = user.getUserId();
        userName = user.getUserName();
        createdDate = user.getCreatedDate();

        UserDtoRest userDtoRest = new UserDtoRest( userId, userName, createdDate );

        return userDtoRest;
    }
}
