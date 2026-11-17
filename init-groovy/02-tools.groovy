import jenkins.model.*
import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation
import hudson.tools.InstallSourceProperty
import hudson.tasks.Maven.MavenInstaller

def instance = Jenkins.getInstance()
println '--> [INIT] Configuring Maven M3'
def mavenDesc = instance.getDescriptorByType(Maven.DescriptorImpl.class)
def mavenInstalls = (mavenDesc.getInstallations() ?: []) as List

if (!mavenInstalls.any { it.name == 'M3' }) {
    def installer = new MavenInstaller('3.9.6')
    def prop = new InstallSourceProperty([installer])
    def m3 = new MavenInstallation('M3', null, [prop])
    mavenInstalls.add(m3)
    mavenDesc.setInstallations(mavenInstalls as MavenInstallation[])
    mavenDesc.save()
    println '--> [INIT] Maven M3 configured with auto-installer 3.9.6!'
}
