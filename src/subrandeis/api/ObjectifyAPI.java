package subrandeis.api;

import subrandeis.entities.Group;
import subrandeis.entities.Person;
import subrandeis.entities.Petition;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyAPI {
    
	static {
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Petition.class);
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Petition.PetitionSignature.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}