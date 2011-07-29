package de.dirent.littlehelper.pages;


import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;


@Import( stylesheet="context:/css/littlehelper.css" )
public class Kreditrechner {

	@Property @Persist
	private boolean showResult;
}
