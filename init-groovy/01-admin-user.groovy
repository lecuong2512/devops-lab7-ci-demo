import jenkins.model.*
import hudson.security.*
import jenkins.install.*

def instance = Jenkins.getInstance()
println '--> [INIT] Configuring Admin User & Security'
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount('admin', 'admin123')
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)

def jlc = jenkins.model.JenkinsLocationConfiguration.get()
jlc.setUrl('http://localhost:8080/')
jlc.save()

instance.save()
println '--> [INIT] Admin User configured (admin / admin123)'
