package com.e3.bizamo.services.parsers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.services.execution.doubles.BadResponseDouble;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServletInputStreamDouble;
import com.e3.bizamo.services.execution.doubles.ServletOutputStreamDouble;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.parsers.APIParser;
import com.e3.bizamo.services.parsers.Version;

public class APIParserShould extends Mockito {
	@Test
	public void constructoreCoverageStub() {
		new APIParser();
	}

	@Test
	public void parseHttpMethod() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/my/path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("post", descriptor.getMethod());
	}

	@Test
	public void parseVersion() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/my/path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals(Version.parse("1.0"), descriptor.getVersion());
		TestCase.assertEquals("my.path", descriptor.getServicePackage());
		TestCase.assertEquals("someservice", descriptor.getName());
	}

	@Test
	public void parsePath() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/my/path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("my.path", descriptor.getServicePackage());
	}

	@Test
	public void parseEmptyPathWhenNotSpecified() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("", descriptor.getServicePackage());
	}

	@Test
	public void parsePathInCaseIndependenyManner() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/My/Path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("my.path", descriptor.getServicePackage());
	}

	@Test
	public void parseShortPath() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("path", descriptor.getServicePackage());
	}

	@Test
	public void parseServiceName() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/my/path/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("someservice", descriptor.getName());
	}

	@Test
	public void parseServiceNameInCaseIndependenyManner() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/my/path/SomeService");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("someservice", descriptor.getName());
	}

	@Test
	public void supportCallWithoutPackage() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		TestCase.assertEquals("", descriptor.getServicePackage());
	}

	@Test
	public void parseSecurityToken() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		when(request.getHeader("token")).thenReturn("12345");
		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		TestCase.assertEquals("12345", parser.getSecurityToken());
	}

	@Test
	public void parseServiceRequest() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		when(request.getInputStream()).thenReturn(
				new ServletInputStreamDouble(
						"{\"s1\":\"string\", \"i1\":\"1\"}"));

		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		RequestDouble serviceRequest = parser
				.getServiceRequest(RequestDouble.class);
		TestCase.assertEquals("string", serviceRequest.getS1());
		TestCase.assertEquals(1, serviceRequest.getI1());
	}

	@Test(expected = ApplicationException.class)
	public void throwOnBadRequest() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		when(request.getInputStream()).thenReturn(
				new ServletInputStreamDouble(
						"{\"fakeField\":\"string\", \"i1\":\"1\"}"));

		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		parser.getServiceRequest(RequestDouble.class);
	}

	@Test(expected = ApplicationException.class)
	public void throwOnMissingRequest() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getPathInfo()).thenReturn("/1.0/someservice");
		when(request.getInputStream()).thenReturn(null);

		APIParser parser = new APIParser();
		parser.setServletRequest(request);
		parser.getServiceRequest(RequestDouble.class);
	}

	@Test
	public void writeResponseToOutputStream() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStreamDouble outputStream = new ServletOutputStreamDouble();
		when(response.getOutputStream()).thenReturn(outputStream);
		APIParser parser = new APIParser();
		parser.setServletResponse(response);
		
		ResponseDouble serviceResponse = new ResponseDouble();
		serviceResponse.setI1(1);
		serviceResponse.setS1("string");
		parser.writeResponse(serviceResponse);
		TestCase.assertEquals("{\"s1\":\"string\",\"i1\":1}", outputStream.getSourceStream().toString());
	}
	
	@Test(expected = ApplicationException.class)
	public void throwOnBadOutputStream() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenThrow(new IOException());
		APIParser parser = new APIParser();
		parser.setServletResponse(response);
	}
	
	@Test(expected=ApplicationException.class)
	public void throwOnJsonProblemWithOutput() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStreamDouble outputStream = new ServletOutputStreamDouble();
		when(response.getOutputStream()).thenReturn(outputStream);
		APIParser parser = new APIParser();
		parser.setServletResponse(response);
		
		BadResponseDouble serviceResponse = new BadResponseDouble();
		serviceResponse.setI1(1);
		serviceResponse.setS1("string");
		parser.writeResponse(serviceResponse);
	}
	@Test
	public void writeApplicationExceptionAsErrorResponse() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStreamDouble outputStream = new ServletOutputStreamDouble();
		when(response.getOutputStream()).thenReturn(outputStream);
		APIParser parser = new APIParser();
		parser.setServletResponse(response);
		
		ApplicationException ex = new ApplicationException("msg");
		parser.writeErrorResponse(ex);
		TestCase.assertEquals("{\"code\":\"ApplicationException\",\"description\":\"msg\"}", outputStream.getSourceStream().toString());
	}
	@Test
	public void writeExceptionAsErrorResponse() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStreamDouble outputStream = new ServletOutputStreamDouble();
		when(response.getOutputStream()).thenReturn(outputStream);
		APIParser parser = new APIParser();
		parser.setServletResponse(response);
		
		RuntimeException ex = new RuntimeException("msg");
		parser.writeErrorResponse(ex);
		TestCase.assertEquals("{\"code\":\"1000\",\"description\":\"msg\"}", outputStream.getSourceStream().toString());
	}

}
