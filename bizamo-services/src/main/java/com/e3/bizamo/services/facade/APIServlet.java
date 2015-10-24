package com.e3.bizamo.services.facade;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.ServiceExecuter;
import com.e3.bizamo.services.parsers.IAPIParser;

/**
 * Servlet implementation class FirstAPI
 */
public class APIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APIServlet() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAPIParser parser = createAndInitAPIParser(request, response);
		ServiceExecuter executer = new ServiceExecuter(parser);
		executer.executePipeline();
	}

	private IAPIParser createAndInitAPIParser(HttpServletRequest request, HttpServletResponse response) {
		String parserKey = this.getServletConfig().getInitParameter("parser");
		if (parserKey==null) parserKey="REST"; 
		IAPIParser parser = IoC.getResolver().resolve(parserKey, IAPIParser.class);
		
		parser.setServletRequest(request);
		parser.setServletResponse(response);
		parser.getSecurityToken();

		return parser;
	}	
}
