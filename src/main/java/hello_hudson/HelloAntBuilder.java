package hello_hudson;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

public class HelloAntBuilder {

	public static void main(String[] args) {

		System.out.println("START");

		Project project = new HelloAntProject();
		project.init();

		BuildLogger logger = new DefaultLogger();
		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setOutputPrintStream(new java.io.PrintStream(System.out));
		logger.setErrorPrintStream(new java.io.PrintStream(System.err));
		logger.setEmacsMode(false);

		project.addBuildListener(logger);

		java.util.Vector list = new java.util.Vector();

		/*
		 * ターゲットを指定 list.add("compile"); のようにすれば任意のターゲットを指定できます。
		 */
		list.add(project.getDefaultTarget());
		project.executeTargets(new java.util.Vector(list));

		System.out.println("DONE");

	}
}