package gradyward.studentunion;

import gradyward.studentunion.ElectionsServlet.ElectionsDatum;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


public class ObjectifyWrapper {
    
	static {
		ObjectifyService.register(ElectionsDatum.class);
		ObjectifyService.register(Person.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}