package lq;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

// Based on: http://code.google.com/appengine/docs/java/datastore/usingjdo.html
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
