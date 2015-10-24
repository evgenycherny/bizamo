package com.e3.bizamo.commons.properties;

import java.util.Properties;

public class PropertyHolder implements IPropertyHolder {
	
	private Properties props;

	@Override
	public void setProperties(Properties props) {
		this.props = props;
		
	}

	@Override
	public Properties getProperties() {
		return this.props;
	}

}
