package br.ufpa.linc.MetriX.parsers;

import java.io.File;

public interface Reader {

	void setApiFile(File apiFile);

	void setRelease(String text);

	void setApiName(String text);

	void setReleaseDate(String text);

	void setApiDownloadURL(String text);

	void verify();

}
