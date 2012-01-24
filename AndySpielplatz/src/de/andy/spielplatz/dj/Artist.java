package de.andy.spielplatz.dj;

public class Artist
{
	private String name;
	private String key;

	public Artist(String name, String key)
	{
		this.setName(name);
		this.setKey(key);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
