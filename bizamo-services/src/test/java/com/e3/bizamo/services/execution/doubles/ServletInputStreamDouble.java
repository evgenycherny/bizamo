package com.e3.bizamo.services.execution.doubles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class ServletInputStreamDouble extends ServletInputStream {

    private final InputStream sourceStream;

    public ServletInputStreamDouble(String inputText) {
        this.sourceStream = new ByteArrayInputStream(inputText.getBytes());
    }

    public final InputStream getSourceStream() {
        return this.sourceStream;
    }


    public int read() throws IOException {
        return this.sourceStream.read();
    }

    public void close() throws IOException {
        super.close();
        this.sourceStream.close();
    }

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		
	}

}
