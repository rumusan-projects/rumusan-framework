package org.rumusanframework.reflect.field;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class InLineResolver extends DefaultFieldResolver {
    private static final Map<Class<?>, Map<Object, String>> CACHE_CLASS_FIELD = new ConcurrentHashMap<>();

    /**
     * Usage :
     * 
     * <pre>
     * Class<?>[] fields = { ClientId.class, ResourceId.class, ClientSecret.class };
     * String columns = ResolverUtils.concate(new InLineResolver(Dao.class, fields));
     * StringBuilder sql = new StringBuilder();
     * sql.append("select ").append(columns).append(" from ").append(OAuthClientDetails.class.getName());
     * System.out.println(sql);
     * 
     * will be print:
     * select clientId,resourceIds,clientSecret from com.pasarsengget.module.domain.OAuthClientDetails
     * </pre>
     * 
     * @param classUsage
     * @param annotationFields
     */
    public InLineResolver(Class<?> classUsage, Class<?>[] annotationFields) {
	super(classUsage, annotationFields);
	concater = ",";
    }

    @Override
    protected Map<Class<?>, Map<Object, String>> getClassCache() {
	return CACHE_CLASS_FIELD;
    }
}