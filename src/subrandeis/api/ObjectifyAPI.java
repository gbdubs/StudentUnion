package subrandeis.api;

import subrandeis.entities.Group;
import subrandeis.entities.Page;
import subrandeis.entities.Person;
import subrandeis.entities.Petition;
import subrandeis.entities.Story;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * An API I wrote to solve some of the strange ways that Objectify works.
 * Static calls make sure that we successfully register the service before we use it.
 * A singular shared Objectify instance reduces runningtime and wasted instantiation time.
 * @author Grady
 *
 */
public class ObjectifyAPI {
    
	private static Objectify objectify;
	
	static {
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Page.class);
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Petition.class);
		ObjectifyService.register(Petition.PetitionSignature.class);
		ObjectifyService.register(Story.class);
    }

    public static Objectify ofy() {
    	if (objectify == null){
    		objectify = ObjectifyService.ofy();
    	}
        return objectify;
    }
    
}