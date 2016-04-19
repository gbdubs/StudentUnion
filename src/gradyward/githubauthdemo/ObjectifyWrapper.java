package gradyward.githubauthdemo;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


public class ObjectifyWrapper {
    
	static {
		ObjectifyService.register(Page.class);
		ObjectifyService.register(Site.class);
		ObjectifyService.register(Human.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}