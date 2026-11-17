import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import org.jenkinsci.plugins.plaincredentials.impl.*
import hudson.util.Secret

def instance = Jenkins.getInstance()
def domain = Domain.global()
def store = instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

println '--> [INIT] Configuring Credentials'

try {
    def sonarToken = new StringCredentialsImpl(
        CredentialsScope.GLOBAL,
        'sonarqube-token',
        'SonarQube token for Lab 7',
        Secret.fromString('sqa_5929b496db43f7a9c408274f87f96ac67ae8c719')
    )
    store.addCredentials(domain, sonarToken)
    println '--> [INIT] sonarqube-token credential added'
} catch (Exception e) {
    println '--> [INIT] Note on sonar token: ' + e.getMessage()
}

try {
    def nexusCreds = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        'nexus-credentials',
        'Nexus credentials for Lab 7',
        'admin',
        'nexus123'
    )
    store.addCredentials(domain, nexusCreds)
    println '--> [INIT] nexus-credentials added'
} catch (Exception e) {
    println '--> [INIT] Note on nexus credentials: ' + e.getMessage()
}
