package org.rumusanframework.repository.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (18 Mar 2018)
 *
 * @param <R>
 */
public interface IDtoDao<R> {
	public R findVoById(Serializable id);

	public List<R> findAllVo();
}