import jenkins.model.*
import hudson.model.*
import hudson.tasks.*
import hudson.plugins.git.*
import hudson.plugins.gradle.*

def jobName = "example"
def repoUrl = "https://github.com/Ach712/gradle.git"
def branch = "main"

def job = Jenkins.instance.createProject(FreeStyleProject, jobName)

// General configuration
job.description = "Freestyle project for building a Gradle project"
job.setDisplayName(jobName)

// GitHub project URL without .git
job.setProperty(new hudson.model.ParametersDefinitionProperty(new hudson.model.StringParameterDefinition('GITHUB_PROJECT_URL', repoUrl - '.git')))

// Source Code Management
def scm = new GitSCM(repoUrl)
scm.branches = [new BranchSpec(branch)]
job.scm = scm

// Build Triggers
def scmTrigger = new SCMTrigger('* * * * *')
job.addTrigger(scmTrigger)

// Build Steps - Invoke Gradle
def gradle = new Gradle()
gradle.tasks = 'clean build'
gradle.useWrapper = true
job.buildersList.add(gradle)

// Build Steps - Execute shell (for Unix-like systems) or batch command (for Windows)
if (System.getProperty('os.name').toLowerCase().contains('windows')) {
    def batchCommand = new BatchFile('gradlew.bat run')
    job.buildersList.add(batchCommand)
} else {
    def shellCommand = new Shell('chmod +x ./gradlew && ./gradlew run')
    job.buildersList.add(shellCommand)
}

// Post-build Actions - Archive the artifacts
def artifactArchiver = new ArtifactArchiver('build/libs/*.jar')
job.publishersList.add(artifactArchiver)

// Save the job configuration
job.save()

// Trigger a build
job.scheduleBuild2(0)
