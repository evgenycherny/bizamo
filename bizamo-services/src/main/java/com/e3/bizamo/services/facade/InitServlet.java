package com.e3.bizamo.services.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.e3.bizamo.commons.properties.IPropertyHolder;
import com.e3.bizamo.commons.properties.PropertyHolder;
import com.e3.bizamo.ioc.inject.IoC;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InitServlet() {
        super();
    }

    @Override 
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);

    	initLogging();
		initIoC();
		initProperties();
    }

	private void initIoC() {
		String classpath = this.getServletConfig().getInitParameter("classpath");
		IoC.init(classpath);
	};
    private void initLogging() {	
		try {
			URL propsPath = this.getServletContext().getResource("WEB-INF/log4j2.xml");
			if (propsPath==null) return;
			System.setProperty("log4j.configurationFile", propsPath.getPath());
			Logger logger = LogManager.getLogger();

			logger.info("BIZAMO is starting up..");
		} catch (IOException e) {
			
		}
    }
    private void initProperties() {
    	Properties props = new Properties();
		
		try {
			URL propsPath = this.getServletContext().getResource("WEB-INF/bizamo.properties");
			props.load(new FileInputStream(new File(propsPath.getPath())));
			PropertyHolder holder = new PropertyHolder();
			holder.setProperties(props);
			IoC.getResolver().register(IPropertyHolder.class, holder);
		} catch (IOException e) {
			
		}
    }
    @Override
    public void destroy() {
    	try {
    		
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			Logger logger = LogManager.getLogger();
			logger.warn("SEVERE problem cleaning up: " + e.getMessage());
		}
    }
}
