/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.reflect.field;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (7 Sept 2017)
 */
public class RecursiveResolver extends DefaultFieldResolver {

  private static final Map<Class<?>, Map<Object, String>> CACHE_CLASS_FIELD = new ConcurrentHashMap<>();

  /**
   * Usage :
   *
   * <pre>
   * public void method() {
   * 	class AnyEntity {
   * 		&#64Id;
   * 		private id;
   * 		&#64Desc;
   * 		private desc;
   *
   * 		&#64;Target({ FIELD })
   * 		&#64;Retention(RUNTIME)
   * 		public @interface Id {
   *    }
   *
   * 		&#64;Target({ FIELD })
   * 		&#64;Retention(RUNTIME)
   * 		public @interface Desc {
   *    }
   *  }
   *
   *
   * 	Class<?>[] fields = { Id.class, Desc.class};
   * 	String columns = ResolverUtils.concate(new RecursiveResolver(Dao.class, fields));
   * 	StringBuilder sql = new StringBuilder();
   * 	sql.append("select ").append(columns).append(" from ").append(AnyEntity.class.getName());
   * 	System.out.println(sql);
   * }
   *
   * will be print:
   * select id.desc from org.rumusanframework.reflect.field.AnyEntity
   * </pre>
   */
  public RecursiveResolver(Class<?> classUsage, Class<?>... annotationFields) {
    super(classUsage, ".", annotationFields);
  }

  @Override
  Map<Class<?>, Map<Object, String>> getClassCache() {
    return CACHE_CLASS_FIELD;
  }
}