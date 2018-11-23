package org.craftedsw.tripservicekata.user;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.Assert.*;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    private static final User PAO = new User();
    private static final User JOBERT = new User();

    @Test
    public void
    shouldInformWhenUsersAreNotFriends(){
        User user = UserBuilder.aUser()
                                .friendsWith(PAO)
                                .build();

        assertThat(user.isFriendsWith(JOBERT), is(false));
    }

    @Test
    public void
    shouldInformWhenUsersAreFriends(){
        User user = UserBuilder.aUser()
                .friendsWith(PAO, JOBERT)
                .build();

        assertThat(user.isFriendsWith(JOBERT), is(true));
    }
}
