package com.epam.newsportal.newsservice.dao.oracledb.impleclipselink;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class TagDaoEL implements ITagDao {

	private static final Logger logger = Logger.getLogger(TagDaoEL.class);
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public TagDTO getById(Long id) throws DaoException {
		try {
			return em.find(TagDTO.class, id);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get tag id = " + id);
		}
	}

	@Override
	public Long insert(TagDTO item) throws DaoException {
		try {
			em.persist(item);
			em.flush();
			return item.getTagId();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot insert new tag");
		}
	}

	@Override
	public void update(TagDTO item) throws DaoException {
		try {
			em.merge(item);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot update tag id = " + item.getTagId());
		}		
	}

	@Override
	public void delete(Long id) throws DaoException {
		try {
			TagDTO tag = em.find(TagDTO.class, id);
			em.remove(tag);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot delete tag id = " + id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TagDTO> getList() throws DaoException {
		try {
			Query query = em.createQuery("select t from TagDTO t"); 
			return (List<TagDTO>)query.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get tag list");
		}
	}
}
