import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import hudson.plugins.git.GitSCM
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.UserRemoteConfig

def instance = Jenkins.getInstance()
def jobName = 'DevOps-Lab7-CI-Pipeline'

println '--> [INIT] Creating Pipeline Job: ' + jobName
try {
    def job = instance.getItem(jobName)
    if (job == null) {
        job = instance.createProject(WorkflowJob.class, jobName)
    }

    def repoUrl = 'https://github.com/lecuong2512/devops-lab7-ci-demo.git'
    def branchSpec = [new BranchSpec('*/main')]
    def remoteConfigs = [new UserRemoteConfig(repoUrl, null, null, null)]
    def scm = new GitSCM(remoteConfigs, branchSpec, null, null, [])
    def flowDef = new CpsScmFlowDefinition(scm, 'Jenkinsfile')
    flowDef.setLightweight(true)

    job.setDefinition(flowDef)
    job.save()
    println '--> [INIT] Pipeline Job ' + jobName + ' created successfully!'
} catch (Exception e) {
    println '--> [INIT] Error creating job: ' + e.getMessage()
}
