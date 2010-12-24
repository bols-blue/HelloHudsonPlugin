package hello_hudson;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Result;
import hudson.tasks.Publisher;


public class HudsonPublisher extends Publisher {

	HudsonPublisher() {
    }

    //ビルド後に実施する処理を記述
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        FilePath[] files = build.getProject().getWorkspace().list("**/javancss*report.xml");

        if (files.length == 0) {
            listener.getLogger().println("not found : javancss report file");
            build.setResult(Result.FAILURE);
        }
        else {
            FilePath root = new FilePath(build.getRootDir());
            FilePath target = new FilePath(root, "javancss_result.xml");

            //workspace 内に生成された javancss-raw-report.xml ファイルを
            //ビルドごとに生成されるディレクトリに
            //javancss_result.xml という名称でコピー
            files[0].copyTo(target);

            HudsonAndroidAction act = new HudsonAndroidAction(build);
            act.setResultFileName(target.getName());

            //ビルド結果に JavaNcssAction インスタンスを追加。
            //ビルド結果の構成を保存する build.xml ファイルに
            //シリアライズされて保存される
            build.addAction(act);
        }

        return true;
    }

    public Descriptor<Publisher> getDescriptor() {
        return DESCRIPTOR;
    }

    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public static final class DescriptorImpl extends Descriptor<Publisher> {
        DescriptorImpl() {
            super(HudsonPublisher.class);
        }

        public String getDisplayName() {
            return "JavaNCSS reports";
        }

        public HudsonPublisher newInstance(StaplerRequest req) throws FormException {
            return new HudsonPublisher();
        }
    }


}
