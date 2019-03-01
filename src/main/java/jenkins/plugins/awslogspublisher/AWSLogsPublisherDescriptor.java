package jenkins.plugins.awslogspublisher;

import com.google.common.base.Strings;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Descriptor for {@link AWSLogsPublisher}. Used as a singleton. The class is
 * marked as public so that it can be accessed from views. See
 * <code>src/main/resources/jenkins/plugins/awslogspublisher/AWSLogsPublisher/*.jelly</code>
 * for the actual HTML fragment for the configuration screen.
 */
public final class AWSLogsPublisherDescriptor extends BuildStepDescriptor<Publisher> {

    protected AWSLogsPublisherDescriptor(Class<? extends Publisher> clazz) {
        super(clazz);
    }

    /**
     * This human readable name is used in the configuration screen.
     */
    public String getDisplayName() {
        return "AWS CloudWatch Logs Publisher";
    }

    /**
     * Performs on-the-fly validation of the form field 'name'.
     *
     * @param value This parameter receives the value that the user has typed.
     * @return Indicates the outcome of the validation. This is sent to the browser.
     * @throws IOException      - parsing error
     * @throws ServletException - connection error
     */
    public FormValidation doCheckFileName(@QueryParameter String value) throws IOException, ServletException {
        if (value.length() == 0)
            return FormValidation.error("Please set a name");
        return FormValidation.ok();
    }

    public boolean isApplicable(Class<? extends AbstractProject> aClass) {
        // Indicates that this builder can be used with all kinds of project types
        return true;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws Descriptor.FormException {
        req.bindJSON(this, json);
        save();
        return true;
    }

}
