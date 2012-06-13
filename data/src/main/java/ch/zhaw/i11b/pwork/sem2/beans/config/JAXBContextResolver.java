package ch.zhaw.i11b.pwork.sem2.beans.config;

import ch.zhaw.i11b.pwork.sem2.beans.*;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

/**
 * The contextResolver of the JSON De-/Serialisation
 * @author oups
 *
 */
@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext>{
    private JAXBContext context;
    private Class[] types = {Message.class, Messages.class, Target.class};

    /**
     * create the JAXBContext.. inside..
     * @throws Exception
     */
    public JAXBContextResolver() throws Exception {
        this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
    }

    /**
     * returns the JSONJAXBContext if type is in types..
     */
    public JAXBContext getContext(Class<?> objectType) {
        for (Class type : this.types) {
            if (type == objectType) {
                return this.context;
            }
        }
        return null;
        
    }
}
