// These are the basic imports that Jenkin's interactive script console
// automatically includes.
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;

// import ScriptApproval class
import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval;

// Debugging Note: Output from this script won't appear in webapp log UI, but
// instead in the system log file, e.g. `/var/log/jenkins/jenkins.log`.

// This is the list of approved Groovy script signatures.
// It is passed into this script via Ansible templating. Each entry in the list will be a string of the form
// "method CLASS_NAME METHOD_NAME ARGUMENT [ARGUMENT]".
// for example:
// 'method groovy.lang.Binding hasVariable java.lang.String'
// once defined, they are visible at http://localhost:8080/scriptApproval


def jenkinsApprovedSignatures = [{{ jenkins_approved_signatures | quote_items | join(',') }}]

def scriptApproval = ScriptApproval.get()
for (signature in jenkinsApprovedSignatures) {
    try {
        println('Adding Groovy approved signature: "' + signature + '"')
        scriptApproval.approveSignature(signature)
    } catch (exception) {
        println('Adding Groovy approved signature failed!: "' + signature + '"')
    }

}

Jenkins.instance.save()