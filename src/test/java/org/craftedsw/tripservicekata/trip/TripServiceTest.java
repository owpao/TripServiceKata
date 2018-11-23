package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TripServiceTest {

	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TRIP_TO_JERUSALEM = new Trip();
	private static final Trip TRIP_TO_COTABATO = new Trip();

	private User loggedInUser;
	private TripService tripService;

	@Before
	public void setUp(){
		tripService = new TestableTripService();
		loggedInUser = REGISTERED_USER;
	}

	@Test(expected = UserNotLoggedInException.class)
	public void shouldThrowAnExceptionWhenUserIsNotLoggedIn(){
		//given
		loggedInUser = GUEST;
	    //when
        tripService.getTripsByUser(UNUSED_USER);
	}

	@Test
	public void shouldNotReturnAnyTripsWhenUsersAreNotFriends(){

		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addTrip(TRIP_TO_JERUSALEM);
		//when
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		assertThat(friendTrips.size(),is(0));
	}

	@Test
	public void shouldReturnAnyTripsWhenUsersAreFriends(){
		User friend = new User();
		friend.addFriend(loggedInUser);
		friend.addFriend(ANOTHER_USER);
		friend.addTrip(TRIP_TO_JERUSALEM);
		friend.addTrip(TRIP_TO_COTABATO);
		//when
		List<Trip> friendTrips = tripService.getTripsByUser(friend);
		assertThat(friendTrips.size(),is(2));
	}

	private class TestableTripService extends TripService{
		@Override
		protected User getLoggedUser() {
			return loggedInUser;
		}

		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
	}
}
