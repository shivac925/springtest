package com.spring.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.ResultDAO;
import com.spring.model.BeanCopyDto;
import com.spring.model.BeanCopyDtoPk;
import com.spring.model.BeanCopyModel;
import com.spring.model.DbProps;
import com.spring.model.EndResult;
import com.spring.model.Login;
import com.spring.model.LoginDto;

@Service("transServ")
@Transactional
public class TransactionsServ {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionsServ.class);
	
	private ResultDAO resultDAO;
	
	EndResult endResult;
	
	@Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;
	
	private Map<String, String> propertiesMap;
	
	@Autowired
	TransactionsServ(ResultDAO resultDAO) {
		this.resultDAO = resultDAO;
		propertiesMap = resultDAO.getPropertiesMap();
		System.out.println(propertiesMap);

	}
	
	public EndResult beanCopy(BeanCopyModel beanCopyModel) {
		endResult= new EndResult();
		BeanCopyDto beanCopy = new BeanCopyDto();
		BeanCopyDtoPk beanCopyPk = new BeanCopyDtoPk();
//		beanCopyPk.setKey1(beanCopyModel.getKey1());
//		beanCopyPk.setKey2(beanCopyModel.getKey2());
		try {
			copyProperties(beanCopyModel, beanCopyPk);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copyProperties(beanCopyModel, beanCopy);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beanCopy.setBeanCopyDtoPk(beanCopyPk);
//		beanCopy.setComments(beanCopyModel.getComments());
//		beanCopy.setDescription(beanCopyModel.getDescription());
		resultDAO.save(beanCopy);
		endResult.setStatus("successs");
		endResult.setData(beanCopy);
		endResult.setErrorCode(0);
		endResult.setMessage("Data Saved");
		return endResult;
	}
	
	public void copyProperties(Object source, Object destination) throws ClassNotFoundException {
//		Field[] fields = destination.getClass().getFields();
		Class<?> cls = Class.forName(destination.getClass().getName());
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}
		BeanUtils.copyProperties(source, destination);
	}
	
	public EndResult userLogin(Login login) {
		endResult= new EndResult();
		if(login.getUserName() != null && !login.getUserName().isEmpty() && 
				login.getPassword() != null && !login.getPassword().isEmpty()) {
			String token = UUID.randomUUID().toString();
			LoginDto loginDto = resultDAO.get(LoginDto.class, login.getUserName());
			if(loginDto == null) {
				endResult.setData(loginDto);
				endResult.setErrorCode(0);
				endResult.setMessage("You are not authorized to login");
				endResult.setStatus("fail");
			} else {
				loginDto.setToken(token);
				loginDto.setLastupdate(new Date());
				loginDto.setIsActive("Y");
				resultDAO.update(loginDto);
				endResult.setData(loginDto);
				endResult.setErrorCode(0);
				endResult.setMessage("Login successfull");
				endResult.setStatus("success");
			}
		} else {
			endResult.setData(null);
			endResult.setErrorCode(0);
			endResult.setMessage("Invalid Login");
			endResult.setStatus("fail");
		}
		return endResult;
	}
	
	public Map<String, String> getProps() {
		return propertiesMap;
	}
	
	public List<DbProps> getMapFromDb() {
		List<DbProps> lis = null;
		try {
			lis = resultDAO.getProperties();
		} catch (DataAccessException | ObjectNotFoundException e) {
			LOGGER.error("my msg: " + e.getMessage());
			resultDAO.refreshCacheDao();
		}
		
		if(lis == null) {
			LOGGER.info("DB Props list size is null, call evict cache...");
			resultDAO.refreshCacheDao();
			lis = resultDAO.getProperties();
			LOGGER.info("Recalled refreshCacheDao in list is null... " + ((lis!=null)?lis.size():"null"));
		} else if(lis.size() == 0) {
			LOGGER.info("DBProps list is null: " + lis.size());
			resultDAO.refreshCacheDao();
			lis = resultDAO.getProperties();
			LOGGER.info("Recalled refreshCacheDao in list size zero... " + ((lis.size()!=0)?lis.size():"zero"));
		}
		return lis;
	}
	
	public String refreshCache() {
		resultDAO.refreshCacheDao();
		return "success";
	}
	
	
	public static void main(String[] args) {
		String[] firstArray = {"fe1", "fe2"};
		List<String> firstList = Arrays.asList("fe1", "fe2");
		String[] secondArray = {"se1", "se2"};
		List<String> secondList = Arrays.asList(secondArray);
		firstList.add("shai");
		firstList.addAll(secondList);
		System.out.println(firstList.size());
	}
	
}
