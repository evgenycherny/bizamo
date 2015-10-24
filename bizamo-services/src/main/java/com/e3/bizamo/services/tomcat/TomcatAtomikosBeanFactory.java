package com.e3.bizamo.services.tomcat;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.naming.ResourceRef;
import org.apache.naming.factory.Constants;

import com.atomikos.beans.PropertyException;
import com.atomikos.beans.PropertyUtils;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosSQLException;
import com.atomikos.jms.AtomikosConnectionFactoryBean;

/**
 * enhancement of com.atomikos.tomcat.BeanFactory (see http://www.atomikos.com/Documentation/Tomcat7Integration35)
 */
public class TomcatAtomikosBeanFactory implements ObjectFactory
{

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?,?> environment) throws NamingException
   {
      if (obj instanceof ResourceRef) { 
         try {
            Reference ref = (Reference) obj;
            String beanClassName = ref.getClassName();
            Class<?> beanClass = null;
            ClassLoader tcl = Thread.currentThread().getContextClassLoader();
            if (tcl != null) {
               try {
                  beanClass = tcl.loadClass(beanClassName);
               } catch (ClassNotFoundException e) {
               }
            } else {
               try {
                  beanClass = Class.forName(beanClassName);
               } catch (ClassNotFoundException e) {
                  e.printStackTrace();
               }
            }
            if (beanClass == null) {
               throw new NamingException("Class not found: " + beanClassName);
            }
            if (AtomikosDataSourceBean.class.isAssignableFrom(beanClass)) {
               return createDataSourceBean(ref, beanClass);
            } else if (AtomikosConnectionFactoryBean.class.isAssignableFrom(beanClass)) {
               return createConnectionFactoryBean(ref, beanClass);
            } else {
               throw new NamingException("Class is neither an AtomikosDataSourceBean nor an AtomikosConnectionFactoryBean: " + beanClassName);
            }

         } catch (Exception ex) {
            throw (NamingException) new NamingException("error creating AtomikosDataSourceBean").initCause(ex);
         }

      } else {
         return null;
      }
   }

   /**
    * create a DataSourceBean for a JMS datasource
    * 
    * @param ref
    * @param beanClass
    * @return
    * @throws InstantiationException
    * @throws IllegalAccessException
    * @throws PropertyException
    * @throws AtomikosSQLException
    * @throws JMSException
    */
   private Object createConnectionFactoryBean(Reference ref, Class<?> beanClass) throws InstantiationException, IllegalAccessException, PropertyException, JMSException
   {
      AtomikosConnectionFactoryBean bean = (AtomikosConnectionFactoryBean) beanClass.newInstance();

      Enumeration<?> en = ref.getAll();
      while (en.hasMoreElements()) {
         RefAddr ra = (RefAddr) en.nextElement();
         String propName = ra.getType();

         if (propName.equals(Constants.FACTORY) || propName.equals("singleton") || propName.equals("description") || propName.equals("scope") || propName.equals("auth")) {
            continue;
         }

         String value = (String) ra.getContent();

         PropertyUtils.setProperty(bean, propName, value);

      }

      bean.init();
      return bean;
   }

   /**
    * create a DataSourceBean for a JDBC datasource
    * 
    * @param ref
    * @param beanClass
    * @return
    * @throws InstantiationException
    * @throws IllegalAccessException
    * @throws PropertyException
    * @throws AtomikosSQLException
    */
   private Object createDataSourceBean(Reference ref, Class<?> beanClass) throws InstantiationException, IllegalAccessException, PropertyException, AtomikosSQLException
   {
      AtomikosDataSourceBean bean = (AtomikosDataSourceBean) beanClass.newInstance();

      Enumeration<?> en = ref.getAll();
      while (en.hasMoreElements()) {
         RefAddr ra = (RefAddr) en.nextElement();
         String propName = ra.getType();

         if (propName.equals(Constants.FACTORY) || propName.equals("singleton") || propName.equals("description") || propName.equals("scope") || propName.equals("auth")) {
            continue;
         }

         String value = (String) ra.getContent();

         PropertyUtils.setProperty(bean, propName, value);

      }

      bean.init();
      return bean;
   }
}