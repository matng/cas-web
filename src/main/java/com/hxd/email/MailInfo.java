package com.hxd.email;

import java.util.Properties;

import com.hxd.util.Global;

public class MailInfo  { 

	// 发送邮件的服务器的IP和端口 
	private String mailServerHost = Global.getConfig("mail.smtp"); 
	private String mailServerPort = Global.getConfig("mail.port"); 
	// 邮件发送者的地址 
	private String fromAddress=Global.getConfig("mail.from"); 
	// 邮件接收者的地址 
	private String toAddress=Global.getConfig("mail.to");  
	// 登陆邮件发送服务器的用户名和密码 
	private String userName =  Global.getConfig("mail.from");
	private String password = Global.getConfig("mail.password");
	// 是否需要身份验证 
	private boolean validate = Boolean.parseBoolean(Global.getConfig("mail.validate")); 
	// 邮件主题 
	private String subject;
	// 邮件的文本内容 
	private String content; 
	// 邮件附件的文件名 
	private String[] attachFileNames; 	
	
	
	
	
	/** 
	  * 获得邮件会话属性 
	  */ 
	public Properties getProperties(){ 
	  Properties p = new Properties(); 
	  p.put("mail.smtp.host", this.mailServerHost); 
	  p.put("mail.smtp.port", this.mailServerPort); 
	  p.put("mail.smtp.auth", validate ? "true" : "false"); 
	  return p; 
	} 
	public String getMailServerHost() { 
	  return mailServerHost; 
	} 
	public void setMailServerHost(String mailServerHost) { 
	  this.mailServerHost = mailServerHost; 
	}
	public String getMailServerPort() { 
	  return mailServerPort; 
	}
	public void setMailServerPort(String mailServerPort) { 
	  this.mailServerPort = mailServerPort; 
	}
	public boolean isValidate() { 
	  return validate; 
	}
	public void setValidate(boolean validate) { 
	  this.validate = validate; 
	}
	public String[] getAttachFileNames() { 
	  return attachFileNames; 
	}
	public void setAttachFileNames(String[] fileNames) { 
	  this.attachFileNames = fileNames; 
	}
	public String getFromAddress() { 
	  return fromAddress; 
	} 
	public void setFromAddress(String fromAddress) { 
	  this.fromAddress = fromAddress; 
	}
	public String getPassword() { 
	  return password; 
	}
	public void setPassword(String password) { 
	  this.password = password; 
	}
	public String getToAddress() { 
	  return toAddress; 
	} 
	public void setToAddress(String toAddress) { 
	  this.toAddress = toAddress; 
	} 
	public String getUserName() { 
	  return userName; 
	}
	public void setUserName(String userName) { 
	  this.userName = userName; 
	}
	public String getSubject() { 
	  return subject; 
	}
	public void setSubject(String subject) { 
	  this.subject = subject; 
	}
	public String getContent() { 
	  return content; 
	}
	public void setContent(String textContent) { 
	  this.content = textContent; 
	} 
} 