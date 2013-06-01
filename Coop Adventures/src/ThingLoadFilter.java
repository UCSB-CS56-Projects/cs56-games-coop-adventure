import java.io.File;

import java.io.FileFilter;

/**
 * Specialized filter for the graphics of the "idle"-images of things, which are used to initialize them.
 * Images with a "2" at the end are animations and are thus filtered. 
 * Currently, only .png pass the filter (easily expanded).
 * @author Gunnar Weibull
 *
 */
public class ThingLoadFilter implements FileFilter{
	String type;

	
	public ThingLoadFilter(String type){
		this.type = type;
	}
	
	
	public boolean accept(File file) {
		
		if(type.equals("normal")){
			String path = file.getAbsolutePath().toLowerCase();

			String notThisExtension = "2.png";	//This is for the animated version of the Thing
			String extension = ".png";
			if (path.endsWith(extension) && !path.endsWith(notThisExtension)){ //&& (path.charAt(path.length() - extension.length() - 1)) == '.')) {
				return true;
			}
		}


		return false;
	}


	public String getDescription() {

		return "Filters depending on the imput parameter. For more info, see javadoc.";
	}

}
