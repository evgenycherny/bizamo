package com.e3.bizamo.services.facade;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;


public class InitServletShould extends Mockito {
	
	@Test
	public void initIoC() throws ServletException, IOException {
		ServletContext servletContext = mock(ServletContext.class);
		when(servletContext.getResource("WEB-INF/bizamo.properties")).thenReturn(this.getClass().getResource("/bizamo.properties"));
		
		ServletConfig servletConfig = mock(ServletConfig.class);
		when(servletConfig.getInitParameter("classpath")).thenReturn("");
		when(servletConfig.getServletContext()).thenReturn(servletContext);
		
		InitServlet servlet = new InitServlet();
		servlet.init(servletConfig);
	}
}
