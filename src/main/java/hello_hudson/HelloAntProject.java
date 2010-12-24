package hello_hudson;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class HelloAntProject extends Project {

	@Override
	public void init() throws BuildException {
		super.init();
		// ビルドファイルを指定
//		ProjectHelper.getProjectHelper().parse(this,
//				new java.io.File(args[0]));;

	}

}
