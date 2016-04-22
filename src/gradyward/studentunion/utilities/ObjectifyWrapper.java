package gradyward.studentunion.utilities;

import gradyward.studentunion.Person;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


public class ObjectifyWrapper {
    
	static {
		ObjectifyService.register(Person.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}