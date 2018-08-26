package com.seancheer.controller;

import com.seancheer.backupservice.BackupFactory;
import com.seancheer.backupservice.IBlogBackup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seancheer.dao.entity.User;
import com.seancheer.dao.interfaces.UserDao;
import com.seancheer.exception.BlogBaseException;

/**
 * 测试使用的controller
 * 
 * @author seancheer
 */
@Controller
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/backup", method = RequestMethod.GET)
	public void Index() {
		IBlogBackup backup = BackupFactory.getCurrentBackup();
        backup.triggerBgTask();
        logger.debug("Backuping failed!");
	}

	@RequestMapping(value = "/passage", method = RequestMethod.GET)
	public void Passage() {
		logger.debug("passage visited...");
	}
	

	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	public String Hello() {
		logger.info("**************** Will query user from database...");
		User user;
		try {
			user = userDao.queryRecordByName("one");
		} catch (BlogBaseException e) {
			logger.error("Query from database failed. Please check!!!", e);
			return null;
		}

		logger.info("user.id:{}  user.name:{} user.type:{} user.isdel:{}", user.getId(), user.getName(),
				user.getUserType(), user.getIsDel());
		return "hello world";
	}

	@RequestMapping(value = "/another", method = RequestMethod.GET)
	public void Another(Model model) {
		model.addAttribute("hello world");
		System.out.println("hello another controller.....");

	}
}
