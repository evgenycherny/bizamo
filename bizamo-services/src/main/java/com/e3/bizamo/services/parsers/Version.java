package com.e3.bizamo.services.parsers;

public class Version implements Comparable<Version> {
	private int major;
	private int minor;
	private int build;
	
	public Version() {
		this(0);
	}
	public Version(int major) {
		this(major, 0);
	}
	public Version(int major, int minor) {
		this(major, minor, 0);
	}
	public Version(int major, int minor, int build) {
		this.major = major;
		this.minor = minor;
		this.build = build;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int minor) {
		this.minor = minor;
	}
	public int getBuild() {
		return build;
	}
	public void setBuild(int build) {
		this.build = build;
	}
	
	@Override
	public String toString() {
		return major + "." + minor + "." + build;
	}
	
	@Override
	public int compareTo(Version version) {
		int compare = new Integer(this.getMajor()).compareTo(new Integer(version.getMajor()));
		if (compare!=0) return compare;
		compare = new Integer(this.getMinor()).compareTo(new Integer(version.getMinor()));
		if (compare!=0) return compare;
		compare = new Integer(this.getBuild()).compareTo(new Integer(version.getBuild()));
		return compare;
	}
	@Override
	public boolean equals(Object object) {
		Version otherVersion = (Version)object;
		return compareTo(otherVersion)==0;
	}
	
	public static Version parse(String versionString) {
		try {
			String[] versionParts = versionString.split("\\.");
			Version version = new Version();
			if (versionParts.length>0) version.setMajor(Integer.parseInt(versionParts[0]));
			if (versionParts.length>1) version.setMinor(Integer.parseInt(versionParts[1]));
			if (versionParts.length>2) version.setBuild(Integer.parseInt(versionParts[2]));
			return version;
		}
		catch(NumberFormatException e) {
			throw new VersionFormatException(String.format("Value [%s] cannot be parsed into valid version identifier", versionString ),e);
		}
		
	}
}
