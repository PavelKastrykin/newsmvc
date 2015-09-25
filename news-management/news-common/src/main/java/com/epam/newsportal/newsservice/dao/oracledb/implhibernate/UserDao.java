package com.epam.newsportal.newsservice.dao.oracledb.implhibernate;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.epam.newsportal.newsservice.dao.IUserDao;
import com.epam.newsportal.newsservice.entity.dto.UserDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class UserDao implements IUserDao {
	
	public static final Logger logger = Logger.getLogger(UserDao.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public UserDTO getUserDTObyName(String userName) throws DaoException {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<UserDTO> userDTO = session.createCriteria(UserDTO.class)
				.add(Restrictions.eq("userName", userName)).list();
		tx.commit();
		return userDTO.get(0);
	}
}
