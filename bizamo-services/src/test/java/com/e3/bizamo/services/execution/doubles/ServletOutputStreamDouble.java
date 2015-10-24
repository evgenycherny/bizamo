package com.e3.bizamo.services.execution.doubles;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletOutputStreamDouble extends ServletOutputStream {

    private final ByteArrayOutputStream sourceStream;

    public ServletOutputStreamDouble() {
        this.sourceStream = new ByteArrayOutputStream();
    }

    public final ByteArrayOutputStream getSourceStream() {
        return this.sourceStream;
    }


    public void write(byte[] buffer) throws IOException {
        this.sourceStream.write(buffer);
    }
    
	@Override
	public void write(int b) throws IOException {
		this.sourceStream.write(b);
	}
    public void close() throws IOException {
        super.close();
        this.sourceStream.close();
    }


	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		
	}
}
