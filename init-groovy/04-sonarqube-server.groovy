import jenkins.model.*
import hudson.plugins.sonar.SonarGlobalConfiguration
import hudson.plugins.sonar.SonarInstallation

println '--> [INIT] Configuring SonarQube Server'
try {
    def sonarConfig = Jenkins.getInstance().getDescriptorByType(SonarGlobalConfiguration.class)
    def inst = new SonarInstallation(
        'SonarQube',
        'http://lab7-sonarqube:9000',
        'sonarqube-token',
        null,
        null,
        null,
        null,
        null,
        null
    )
    sonarConfig.setInstallations(inst)
    sonarConfig.save()
    println '--> [INIT] SonarQube Server configured successfully!'
} catch (Exception e) {
    println '--> [INIT] Note on SonarQube config: ' + e.getMessage()
}
