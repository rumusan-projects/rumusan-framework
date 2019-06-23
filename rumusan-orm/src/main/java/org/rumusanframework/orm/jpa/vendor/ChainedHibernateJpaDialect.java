/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.jpa.vendor;

import java.util.HashSet;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (12 Mar 2018)
 */
public class ChainedHibernateJpaDialect extends HibernateJpaDialect {

  private static final long serialVersionUID = 4425692243075506813L;

  private transient Set<PersistenceExceptionTranslator> translators;

  public void addTranslator(PersistenceExceptionTranslator translator) {
    if (translators == null) {
      translators = new HashSet<>();
    }

    translators.add(translator);
  }

  @Override
  public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
    for (PersistenceExceptionTranslator pet : translators) {
      DataAccessException dae = pet.translateExceptionIfPossible(ex);
      if (dae != null) {
        return dae;
      }
    }

    return super.translateExceptionIfPossible(ex);
  }
}