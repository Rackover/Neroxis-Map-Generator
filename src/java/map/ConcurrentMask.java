package map;

import java.nio.file.Path;

public strictfp interface ConcurrentMask {

	public ConcurrentMask mockClone();
	public String getName();

	public void writeToFile(Path path);
}
