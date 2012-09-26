/*
 * Copyright 2010 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package azkaban.utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import org.apache.log4j.Logger;

/**
 * The mailman send you mail, if you ask him
 * 
 * @author jkreps
 * 
 */
public class Mailman {

    private static Logger logger = Logger.getLogger(Mailman.class.getName());

    private final String _mailHost;
    private final String _mailUser;
    private final String _mailPassword;
    private final String _mailSender;

    public Mailman(String mailHost, String mailUser, String mailPassword, String mailSender) {
        this._mailHost = mailHost;
        this._mailUser = mailUser;
        this._mailPassword = mailPassword;
        this._mailSender = mailSender;
    }

    public void sendEmail(String fromAddress, List<String> toAddress, String subject, String body)
            throws MessagingException {
//        Properties props = new Properties();
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.put("mail.host", _mailHost);
//        props.put("mail.user", _mailUser);
//        props.put("mail.password", _mailPassword);
//
//        Session session = Session.getDefaultInstance(props);
//        Message message = new MimeMessage(session);
//        InternetAddress from = new InternetAddress(fromAddress == null? "dolores.umbridge@azkaban.com" : fromAddress, false);
//        message.setFrom(from);
//        for(String toAddr: toAddress)
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr, false));
//        try {
//        	message.setSubject(subject);
//            message.setText(body);
//            message.setSentDate(new Date());
//            Transport.send(message);
//        }
//        catch (Exception e) {
//        	logger.error(e);
//        }
    	
    	  SimpleEmail email = new SimpleEmail();
    	  try{
//    		  email.setHostName(_mailHost);
    		  email.setHostName("email.corp.linkedin.com");
    		  
//        	  for(String addr : toAddress) {
//        		  email.addTo(addr);
//        	  }
        		
    		  email.addTo("cyu@linkedin.com");
        	  email.addTo("hadoop-etl-notifications@linkedin.com");
        	  email.addTo("azkaban-test@linkedin.com");
        			  
        	  email.setFrom(fromAddress);
        	  email.setSubject(subject);
        	  email.setMsg(body);
        	  email.setDebug(true);
        	  email.send();
    	  }
    	  catch (EmailException e)
    	  {
    		  logger.error(e);
    	  }
    	  catch (Exception e)
    	  {
    		  logger.error(e);
    	  }
    	  

    }

    public void sendEmailIfPossible(String fromAddress,
                                    List<String> toAddress,
                                    String subject,
                                    String body) {
        try {
            sendEmail(fromAddress, toAddress, subject, body);
        } catch(AddressException e) {
            logger.warn("Error while sending email, invalid email: " + e.getMessage());
        } catch(MessagingException e) {
            logger.warn("Error while sending email: " + e.getMessage());
        }
    }

}